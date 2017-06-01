package jaranddex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;


public class ReadClassNameFromDex {
	private static int[] class_idx = null;
	private static int[] descriptor_idx = null;
	private static int[] string_data_off = null;
	private static String[] className = null;
	private static ReadClassInDex mReadClassInDex = new ReadClassInDex();
	private static int class_def_off = 0;
	private static RandomAccessFile r = null;  //用RandomAccessFile可以随机访问到文件特定字节
	public static int countClass = 0;
	
	public static void setClass_idx(){
		File file = new File("G:/已测apk/Test_dx/dex");
		File[] allDexFile = file.listFiles();
		File outputFile = new File("G:/已测apk/Test_dx/类名对比/dex.txt");  //存放数组数据的文件 
		FileWriter out = null;
		try {
			out = new FileWriter(outputFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  //文件写入流  
		for(File temp : allDexFile){
			mReadClassInDex.readFileByBytes(temp);
			int count_classINdex = mReadClassInDex.getSum();
			class_idx = new int[count_classINdex];
			descriptor_idx = new int[count_classINdex];
			string_data_off = new int[count_classINdex];
			className = new String[count_classINdex];
			ReadClassOffsetInDex.readOffsetByBytes(temp);
			class_def_off = ReadClassOffsetInDex.getOffset();//获取class_def的偏移地址（十进制）
			try {
				r = new RandomAccessFile(temp,"r");
				for(int i=0;i<count_classINdex;i++){
					r.seek(class_def_off+i*32);//跳到offset处
					class_idx[i] = read4Bytes();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//以上完成读取所有的class_idx
			
			try {
				r.seek(68);
				int type_ids_off = read4Bytes();
				for(int i=0;i<count_classINdex;i++){
					r.seek(type_ids_off + 4* class_idx[i]);
					descriptor_idx[i] = read4Bytes();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//以上完成读取所有的descriptor_idx
			
			try {
				r.seek(60);
				int string_ids_off = read4Bytes();
				for(int i=0;i<count_classINdex;i++){
					r.seek(string_ids_off + 4* descriptor_idx[i]);
					string_data_off[i] = read4Bytes();
//					System.out.println(string_data_off[i]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//以上完成读取所有的descriptor_idx
			//接下来就是真正的类名（类类型）了。
			
			try {
				for(int i=0;i<count_classINdex;i++){//不能全部输出显示，控制台地方有限会覆盖前面的内容
					r.seek(string_data_off[i]);
					int classNameLength = decodeUleb128(r);
					className[i] = readNBytes(r,classNameLength);//原始类名，要改成精简类名，并加上.class
					int start = className[i].lastIndexOf("/");
					if(start==-1)//La.class这种情况,要去掉L
						start = 0;
					int end = className[i].lastIndexOf(";");
					className[i] = className[i].substring(start+1, end);
					className[i]+=".class";
					out.write(className[i]+"\r\n");  
				}
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static int read4Bytes(){ //一次读四个字节
		int idx = 0;
		int times=0;
		try {
			int tempbyte;
			String hex;
			for(times=0;times<4;times++){
				tempbyte=r.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				idx+=j*Math.pow(256, times);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return idx;
	}
	
	private static int hexToInt(String str){
		Integer in = Integer.valueOf(str,16);
		return in;
	}
	
	public static String toStringHex(String s) {	//将16进制转化为ASCII字符
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
    public static String readNBytes(RandomAccessFile dexFile,int N){	//一次读N个字节
		int tempbyte;
		String hex;
		String strClassName = new String();
		try {
			for(int times=0;times<N;times++){
				tempbyte = dexFile.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				strClassName+=hex;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return toStringHex(strClassName);
	}

	public static int decodeUleb128(RandomAccessFile dexFile) throws IOException {
		Byte tempbyte;
		String str;
		String result;
		char c;
		int sum = 0;
		tempbyte = dexFile.readByte();
		str = byteToBit(tempbyte);
		c = str.charAt(0);
		result = str.substring(1);
		while (c != '0') {
			tempbyte = dexFile.readByte();
			str = byteToBit(tempbyte);
			c = str.charAt(0);
			str = str.substring(1);
			str += result;
			result = str;
		}
		int pow = result.length() - 1;
		for (int i = 0; i < result.length(); i++) {
			sum += (result.charAt(i) - '0') * Math.pow(2, pow);
			pow--;
		}
		return sum;
	}

	/**
	 * 把byte转为字符串的bit
	 */
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 0) & 0x1);
	}

	
	public static void main(String[] args){
		setClass_idx();//获得dex中文件的类名并写入dex.txt中
		//下面是比较dex.txt 和 jar.txt
		//用python去比较或者用第三方包，commonsIO
		PythonInterpreter interpreter = new PythonInterpreter(); //调用python程序
		interpreter.execfile("G:\\已测apk\\Test_dx\\类名对比\\compare.py");
		PyFunction func = (PyFunction)interpreter.get("getLength",PyFunction.class);//先获取文件长度
		PyObject pyobj = func.__call__(new PyString("G:\\已测apk\\Test_dx\\类名对比\\dex.txt"));
		int countClass = pyobj.asInt();
		System.out.println(countClass);
		
		func = (PyFunction)interpreter.get("equal",PyFunction.class);//再进行对比，内容一致？长度等于原长度？
		pyobj = func.__call__(new PyString("G:\\已测apk\\Test_dx\\类名对比\\dex.txt"),
										new PyString("G:\\已测apk\\Test_dx\\类名对比\\jar.txt"),
										new PyInteger(countClass));
		String flag = pyobj.toString();
		flag = flag.toUpperCase();
		if(flag.equals("TRUE")){
			System.out.println("success! dex 与 jar包中的类一一对应");
		}else if(flag.equals("FALSE")){
			System.out.println("FAIL! dex 与 jar包中的类不对应");
		}else{
			System.out.println("ERROR " + flag);
		}
	}
}
