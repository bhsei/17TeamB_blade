package methodcountInDex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllMethodsInDex{
	private static RandomAccessFile dexFile = null;
	private static int string_ids_off = 0;
	private static int type_ids_off = 0;
	private static int method_ids_size = 0;
	private static int method_ids_off = 0;
	private static Set<String> allClassName = null;//存放所有类名
	private static Map<String,List<String> > allMethodsName = null;//存放类中的所有方法名
	private static File allType = null;
	private static FileWriter outAllType = null;
	
	public static Set<String> allDexClassName = null;//存放所有dex的原始类名
	public static Map<String,List<String> > allDexMethodName = null;//存放所有dex的方法名
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("G:/已测apk/Test_dx/dex");
		File[] allDexFile = file.listFiles();
		File outputFile = new File("G:/huaweiPro/项目资料/class文件格式/AllMethodsNameInDex.txt");  //存放数组数据的文件 
		FileWriter out = new FileWriter(outputFile);
		allType = new File("F:/Methods'Type.txt");
		outAllType = new FileWriter(allType);
		
		allDexClassName = new HashSet<String>();
		allDexMethodName = new HashMap<String,List<String> >();
		
		for(File temp : allDexFile){
			dexFile = new RandomAccessFile(temp,"r");
			dexFile.seek(60);
			string_ids_off = read4Bytes(dexFile);//String表的偏移
			dexFile.seek(68);
			type_ids_off = read4Bytes(dexFile);//type表的偏移
			dexFile.seek(88);
			method_ids_size = read4Bytes(dexFile);//method个数
			dexFile.seek(92);
			method_ids_off = read4Bytes(dexFile);//method表的偏移
			readAllMethodsNameInDex(method_ids_size,method_ids_off,dexFile,out,outAllType);
		}
		out.close();
		outAllType.close();
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("F:/allDexClassName.txt")));
		oos.writeObject(allDexClassName);
		oos.close();
	}
	
	private static void readAllMethodsNameInDex(int method_ids_size,int method_ids_off,RandomAccessFile dexFile,FileWriter out,FileWriter outAllType) throws Exception{
		allClassName = new HashSet<String>();
		allMethodsName = new HashMap<String,List<String> >();
		for(int i=0;i<method_ids_size;i++){
			dexFile.seek(method_ids_off + i*8);//跳到每个method_id_item的偏移处
			int class_idx = read2Bytes(dexFile);//方法所属类名索引,不一定是引用类型，有可能就是普通的int型
			dexFile.readUnsignedShort();
			int name_idx = read4Bytes(dexFile);//方法名索引
			
			dexFile.seek(type_ids_off + 4*class_idx);
			int descriptor_idx = read4Bytes(dexFile);
			dexFile.seek(string_ids_off + 4*descriptor_idx);
			int string_data_off = read4Bytes(dexFile);
			dexFile.seek(string_data_off);
			int classNameLength = decodeUleb128(dexFile);
			String className = readNBytes(dexFile,classNameLength);//找到一个方法所属的类型名(可能为基本型)
			outAllType.write(className + "\r\n");
			String classNameCopy = className;
			allDexClassName.add(classNameCopy);//所有dex中的类，为了统计到底多出那些类
			
			className = className.replace('/', '\\');
			int end = className.lastIndexOf(";");
			if(end==-1){//基本类型
				className = "基本类型：" + className;
			}else if(className.charAt(0)=='L'){//引用类型-类
				className = className.substring(0,end);
				className+=".class";
			}else{
				className = "其他引用类型：" + className;
			}
			allClassName.add(className);
			
			dexFile.seek(string_ids_off + 4*name_idx);
			string_data_off = read4Bytes(dexFile);
			dexFile.seek(string_data_off);
			int methodNameLength = decodeUleb128(dexFile);
			String methodName = readNBytes(dexFile,methodNameLength);//方法名
			
			//下面将方法名加到所属类名下面
			List<String> methodNames = null;
			if(allMethodsName.containsKey(className)){//说明之前这个类已经加入，list一定非空
				methodNames = allMethodsName.get(className);
				methodNames.add(methodName);
			}else{
				methodNames = new ArrayList<String>();
				methodNames.add(methodName);
			}
			allMethodsName.put(className, methodNames);
			
			allDexMethodName.put(classNameCopy, methodNames);//所有dex的类及其方法，为了统计到底多出那些方法
		}
		
		//给类名排序
		List<String> allClassNameList = new ArrayList<String>(allClassName);
		Collections.sort(allClassNameList);
		for(int i=0;i<allClassNameList.size();i++){
			String className = allClassNameList.get(i);
			int index = className.lastIndexOf("\\");
			if(index==-1 && className.charAt(0)=='L'){//La.class这种情况,要去掉L
				index = 0;
			}
			if(className.contains("基本类型：") ||className.contains("其他引用类型：")){
				index = -1;
			}
			out.write(className.substring(index+1) + "\r\n");//输出类型名
			List<String> list = allMethodsName.get(className);
			for(int j=0;j<list.size();j++){
				out.write("\t" + list.get(j) + "\r\n");//输出类中的方法名
			}
		}
	}
	
	public static int read4Bytes(RandomAccessFile dexFile){
		int idx = 0;
		int times=0;
		try {
			int tempbyte;
			String hex;
			for(times=0;times<4;times++){
				tempbyte=dexFile.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				idx += j*Math.pow(256, times);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return idx;
	}
	
	public static int read2Bytes(RandomAccessFile dexFile){
		int idx = 0;
		int times=0;
		try {
			int tempbyte;
			String hex;
			for(times=0;times<2;times++){
				tempbyte=dexFile.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				idx += j*Math.pow(256, times);
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
	
}
