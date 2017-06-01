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
import java.util.List;
import java.util.Map;

public class DirectAndVirtualMethodInDex {
	private static RandomAccessFile dexFile = null;
	private static int class_defs_size = 0;
	private static int class_defs_off = 0;
	private static int type_ids_off = 0;
	private static int string_ids_off = 0;
	private static int method_ids_off = 0;
	private static List<String> allClassName = null;
	private static Map<String,List<String> > directAndVirtualName = null;
	
	public static List<String> allDexClassName = null;
	private static Map<String,List<String> > allDexdirectAndVirtualName = null;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("G:/已测apk/Test_dx/dex");
		File[] allDexFile = file.listFiles();
		File outputFile = new File("G:/huaweiPro/项目资料/class文件格式/DirectAndVirtualMethodNameInDex.txt");  //存放数组数据的文件 
		FileWriter out = null;
		out = new FileWriter(outputFile);
		
		allDexClassName = new ArrayList<String>();
		allDexdirectAndVirtualName = new HashMap<String,List<String> >();
		
		for(File temp : allDexFile){
			dexFile = new RandomAccessFile(temp,"r");
			dexFile.seek(96);
			class_defs_size = read4Bytes(dexFile);//类的个数
			dexFile.seek(100);
			class_defs_off = read4Bytes(dexFile);//类表的偏移
			if(class_defs_off ==0){
				continue;
			}
			dexFile.seek(68);
			type_ids_off = read4Bytes(dexFile);//type表的偏移
			dexFile.seek(60);
			string_ids_off = read4Bytes(dexFile);//String表的偏移
			dexFile.seek(92);
			method_ids_off = read4Bytes(dexFile);//method表的偏移
			readMethodNameFromDex(class_defs_size,class_defs_off,dexFile,out);
		}
		out.close();
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("F:/partDexClassName.txt")));
		oos.writeObject(allDexClassName);
		oos.close();
	}
	
	private static void readMethodNameFromDex(int class_defs_size,int class_defs_off,RandomAccessFile dexFile,FileWriter out) throws Exception{
		allClassName = new ArrayList<String>();
		directAndVirtualName = new HashMap<String,List<String> >();
		for(int i = 0;i<class_defs_size;i++){
			dexFile.seek(class_defs_off + i*32);//跳到每个class_def_item的偏移处
			int class_idx = read4Bytes(dexFile);
			dexFile.seek(type_ids_off + 4*class_idx);
			int descriptor_idx = read4Bytes(dexFile);
			dexFile.seek(string_ids_off + 4*descriptor_idx);
			int string_data_off = read4Bytes(dexFile);
			
			
			dexFile.seek(string_data_off);
			int classNameLength = decodeUleb128(dexFile);
			String className = readNBytes(dexFile,classNameLength);//原始类名，要改成精简类名，并加上.class
			className = toStringHex(className);
//			String className = readString();//原始类名，要改成精简类名，并加上.class
			String classNameCopy = className;
			allDexClassName.add(classNameCopy);
			/*int start = className.lastIndexOf("/");
			int end = className.lastIndexOf(";");
			className = className.substring(start+1, end);
			className+=".class";
			out.write(className+"\r\n");//输出类名。接下来要输出方法*/
			className = className.replace('/', '\\');
			int end = className.lastIndexOf(";");
//			System.out.println(string_data_off + "\t" +className);
/*			if(end==-1){
				
			}//可能是readName()出了问题，就是uleb8读取问题*/
			
			className = className.substring(0,end);
			className+=".class";	//为了与jar包中类名排序统一度量标准，要改一些字符，对排序很重要！！
			allClassName.add(className);
			directAndVirtualName.put(className, new ArrayList<String>());
			
			dexFile.seek(class_defs_off + i*32);//跳到每个class_def_item的偏移处
			readNBytes(dexFile,24);
			int class_data_off = read4Bytes(dexFile);
			if(class_data_off==0){//此类没有类数据
				continue;
			}else{		//指向class_data_item
				dexFile.seek(class_data_off);
				int static_fields_size = decodeUleb128(dexFile);
				int instance_fields_size = decodeUleb128(dexFile);
				int direct_methods_size = decodeUleb128(dexFile);
				int virtual_methods_size = decodeUleb128(dexFile);
				int j;
				for(j=0;j<static_fields_size;j++){//读static_fields
					decodeUleb128(dexFile);
					decodeUleb128(dexFile);
				}
				for(j=0;j<instance_fields_size;j++){//读instance_fields
					decodeUleb128(dexFile);
					decodeUleb128(dexFile);
				}
				/*
				 * 第一次循环时method_idx_diff直接指向method table中对应的项
                	从第二次开始method_idx_diff就是与前一次的偏移值，方法在这个列表中一定是递增的
				 */
				int method_idx = 0;
				int[] method_index = new int[direct_methods_size];
				for(j=0;j<direct_methods_size;j++){//读direct_methods
					int method_idx_diff = decodeUleb128(dexFile);
//					out.write("\t" +method_idx_diff+"\r\n");
					method_idx+=method_idx_diff;
					method_index[j] = method_idx;//指向方法列表中的index
					decodeUleb128(dexFile);
					decodeUleb128(dexFile);
				}
				
				method_idx = 0;
				int[] virtual_method_index = new int[virtual_methods_size];
				for(j=0;j<virtual_methods_size;j++){//读virtual_methods
					int method_idx_diff = decodeUleb128(dexFile);
					method_idx+=method_idx_diff;
					virtual_method_index[j] = method_idx;
					decodeUleb128(dexFile);
					decodeUleb128(dexFile);
				}
				
				List<String> methodNameInOneClass = new ArrayList<String>();
	
				if(method_ids_off!=0){
					for(j=0;j<direct_methods_size;j++){//真正开始读direct方法名字
						dexFile.seek(method_ids_off + 8*method_index[j]);//method_id_item
						readNBytes(dexFile,4);
						int name_idx = read4Bytes(dexFile);
						dexFile.seek(string_ids_off + 4*name_idx);
						int stringOffset = read4Bytes(dexFile);
						dexFile.seek(stringOffset);
//						String methodName = readString();//这个类直接定义的方法名,readString()有时有bug
						int methodNameLength = decodeUleb128(dexFile);
						String methodName = readNBytes(dexFile,methodNameLength);
						methodName = toStringHex(methodName);//这个类定义的virtual方法名
						/*out.write("\t" +methodName+"\r\n");*/
						methodNameInOneClass.add(methodName);
					}
					
					for(j=0;j<virtual_methods_size;j++){ //真正开始读virtual方法名字
						dexFile.seek(method_ids_off + 8*virtual_method_index[j]);//method_id_item
						readNBytes(dexFile,4);
						int name_idx = read4Bytes(dexFile);
						dexFile.seek(string_ids_off + 4*name_idx);
						int stringOffset = read4Bytes(dexFile);
						dexFile.seek(stringOffset);
//						String methodName = readString();//这个类定义的virtual方法名
						int methodNameLength = decodeUleb128(dexFile);
						String methodName = readNBytes(dexFile,methodNameLength);
						methodName = toStringHex(methodName);//这个类定义的virtual方法名
						/*out.write("\t" +methodName+"\r\n");*/
						methodNameInOneClass.add(methodName);
					}
					directAndVirtualName.put(className, methodNameInOneClass);
					
					allDexdirectAndVirtualName.put(classNameCopy, methodNameInOneClass);
				}
			}
		}
		Collections.sort(allClassName);//给类名排序
		for(int i=0;i<allClassName.size();i++){
			String className = allClassName.get(i);
			int index = className.lastIndexOf("\\");
			if(index==-1){//La.class这种情况,要去掉L
				index = 0;
			}
			out.write(className.substring(index+1) + "\r\n");
//			out.write(className + "\r\n");
			List<String> list = directAndVirtualName.get(className);
			for(int j=0;j<list.size();j++){
				out.write("\t" + list.get(j) + "\r\n");
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
	
	private static int hexToInt(String str){
		Integer in = Integer.valueOf(str,16);
		return in;
	}	
	
	/**
     * Reads a variable-length unsigned LEB128 value.  Does not attempt to
     * verify that the value is valid.
     *
     * @throws IOException if we run off the end of the file
     */
    static int readUnsignedLeb128() throws IOException {
        int result = 0;
        byte val;

        do {
            val = dexFile.readByte();
            result = (result << 7) | (val & 0x7f);
        } while (val < 0);

        return result;
    }

    /**
     * Reads a UTF-8 string.
     *
     * We don't know how long the UTF-8 string is, so we have to read one
     * byte at a time.  We could make an educated guess based on the
     * utf16_size and seek back if we get it wrong, but seeking backward
     * may cause the underlying implementation to reload I/O buffers.
     */
    static String readString() throws IOException {
        int utf16len = readUnsignedLeb128();
        byte inBuf[] = new byte[utf16len * 3];      // worst case
        int idx;

        for (idx = 0; idx < inBuf.length; idx++) {
            byte val = dexFile.readByte();
            if (val == 0)
                break;
            inBuf[idx] = val;
        }

        return new String(inBuf, 0, idx, "UTF-8");
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
		return strClassName;
	}
        
    public static int decodeUleb128(RandomAccessFile dexFile) throws IOException{
    	Byte tempbyte;
    	String str;
    	String result;
    	char c;
    	int sum = 0;
    	tempbyte = dexFile.readByte();
    	str = byteToBit(tempbyte);
    	c = str.charAt(0);
    	result = str.substring(1);
    	while(c!='0'){
    		tempbyte = dexFile.readByte();
    		str = byteToBit(tempbyte);
    		c = str.charAt(0);
    		str = str.substring(1);
    		str+=result;
    		result = str;
    	}
    	int pow = result.length()-1;
    	for(int i=0;i<result.length();i++){
    		sum+=(result.charAt(i)-'0')*Math.pow(2, pow);
    		pow--;
    	}
    	return sum;
    }
    
    /** 
     * 把byte转为字符串的bit 
     */  
    public static String byteToBit(byte b) {  
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
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
}
