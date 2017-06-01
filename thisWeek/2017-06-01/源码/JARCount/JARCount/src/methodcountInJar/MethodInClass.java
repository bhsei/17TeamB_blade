package methodcountInJar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;

public class MethodInClass {
	private static RandomAccessFile classFile = null;
	private static BatchRename jar2zip = new BatchRename("jar","zip","G:/已测apk/Test_dx/jar");
	private static int methodNumber = 0;
	private static String[] UTF8_CONSTANT_Utf8_info = null;//用于记录常量池中的utf-8常量
	private static Vector<File> ver=new Vector<File>();
	private static Vector<File> verClass=new Vector<File>();
	private static File outputFile = new File("G:/huaweiPro/项目资料/class文件格式/MethodNameInJar.txt");  //存放类名的文件
	private static FileWriter out = null;
	private static List<String> allClassName = null;
	private static Map<String,List<String> > allMethodNameInJar = null;
	private static int classNumber = 0;
	private static List<String> trueAllClassName = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		out = new FileWriter(outputFile);
		jar2zip.main(null);
		BatchUnZipper.main(null);//将jar解压为文件夹,在ClassInJar中
		File file = new File("G:/已测apk/Test_dx/ClassInJar");
		File[] allJarFile = file.listFiles();//得到所有的jar文件夹
		methodNumber = 0;
		classNumber = 0;
		for(File temp : allJarFile){//获得一个jar文件夹
			getClassInJarFile(temp);
			for(int i=0;i<verClass.size();i++){
				methodNumber+=MethodNumberPerClass(verClass.get(i));//获得一个类中的方法及方法名
				/*System.out.println("目前方法总数：" + methodNumber);*/
			}
			classNumber +=verClass.size();
			verClass = null;
			
			Collections.sort(allClassName);//给类名排序
			for(int i=0;i<allClassName.size();i++){
				String className = allClassName.get(i);
				int index = className.lastIndexOf('\\');
				out.write(className.substring(index+1) + "\r\n");
				List<String> list = allMethodNameInJar.get(className);
				for(int j=0;j<list.size();j++){
					out.write("\t" + list.get(j) + "\r\n");
				}
			}
		}
		System.out.println("class总数：" + classNumber);
		out.close();
		
		FileUtils.writeLines(new File("F:/temp.txt"), trueAllClassName);
//		File file = new File("G:\\huaweiPro\\项目资料\\class文件格式\\Test.class");
//		out = new FileWriter(outputFile);
//		File file = new File("G:\\已测apk\\Test_dx\\ClassInJar\\classes-dex2jar\\com\\alipay\\b\\a\\a\\a\\a\\c.class");
//		long count = MethodNumberPerClass(file);
//		System.out.println(count);
//		out.close();
	}
	
	private static void getClassInJarFile(File file) throws Exception{//获取jar文件夹中所有class，并算出方法数量
		ver.add(file);
		verClass=new Vector<File>();
		allClassName = new ArrayList<String>();
		allMethodNameInJar = new HashMap<String,List<String> >();
		while(ver.size()>0){
			File[] files = ver.get(0).listFiles();//获取该文件夹下所有的文件(夹)名
			ver.remove(0);
			int len=files.length;
			for(int i=0;i<len;i++){
				if(files[i].isDirectory())    //如果是目录，则加入队列。以便进行后续处理
                    ver.add(files[i]);
				else if(files[i].getName().endsWith(".class")){
					verClass.add(files[i]);
					
					String str = files[i].getAbsolutePath().replaceAll("LXHLXH", "");
					
					allClassName.add(str);//完整路径名
					allMethodNameInJar.put(str, new ArrayList<String>());
				}
			}
		}
		trueAllClassName.addAll(allClassName);
//		int len = verClass.size();
//		for(int i=0;i<len;i++){
//			methodNumber+=MethodNumberPerClass(verClass.get(i));
//		}
	}
	
	private static long MethodNumberPerClass(File file) throws Exception{//一个class中方法的数量
		System.out.println("current File:" + file.getAbsolutePath());
		/*out.write(file.getName() + "\r\n");*/
		long sum = 0;// method数量
		int tempbyte;
		String hex;
		long offset;
		try {
			classFile = new RandomAccessFile(file,"r");
			classFile.seek(8);
			long constant_pool_count = read2Bytes()-1;//是常量池中的常量个数
			UTF8_CONSTANT_Utf8_info = new String[(int) (constant_pool_count + 1)];
			System.out.println("常量池中常量的个数："+ constant_pool_count);
			long length;
			classFile.seek(10);
			offset = 10;
			for(int i=1;i<=constant_pool_count;i++){
				System.out.println("#" + i);
				System.out.println("这个常量的起始位置：" + offset);
				tempbyte = classFile.read();//读tag
				offset++;
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int tag = hexToInt(hex);//判断类型
				if(tag==1){			//UTF8_info
					length = read2Bytes();
					String str = readNBytes(length);
					offset+=2+length;
					UTF8_CONSTANT_Utf8_info[i] = str;//只记录UTF-8常量
//					System.out.println(str);
				}else if(tag==12){	//NameAndType_info
					readNBytes(4);
					offset+=4;
				}else if(tag==3){	//Integer_info
					readNBytes(4);
					offset+=4;
				}else if(tag==4){	//Float_info
					readNBytes(4);
					offset+=4;
				}else if(tag==5){	//Long_info
					readNBytes(8);
					offset+=8;
					i++;			//读到long和double要多跳转一个
				}else if(tag==6){	//Double_info
					readNBytes(8);
					offset+=8;
					i++;
				}else if(tag==8){	//String_info
					readNBytes(2);
					offset+=2;
				}else if(tag==7){	//Class_info
					readNBytes(2);
					offset+=2;
				}else if(tag==9){	//Fieldref_info
					readNBytes(4);
					offset+=4;
				}else if(tag==10){	//Methodref_info
					readNBytes(4);
					offset+=4;
				}else if(tag==11){	//InterfaceMethodref_info
					readNBytes(4);
					offset+=4;
				}else if(tag==15){ //MethodHandle_info
					readNBytes(3);
					offset+=3;
				}else if(tag==16){ //MethodType_info
					readNBytes(2);
					offset+=2;
				}else if(tag==18){ //InvokeDynamic_info
					readNBytes(4);
					offset+=4;
				}
			}
			System.out.println("常量池结尾offset: " + offset);
			
			readNBytes(6);
			offset+=6;
			length = read2Bytes();//interfaces_count
			offset+=2;
			System.out.println("interfaces: " + length);
			for(int i = 0;i<length;i++){
				readNBytes(2);
			}
			offset+=length*2;
			length = read2Bytes();//fields_count
			System.out.println("fields起始位置" + offset + " 大小： " + length);
			offset+=2;
			for(int i=0;i<length;i++){//读Field_info
				readNBytes(6);
				offset+=6;
				long attributes_count = read2Bytes();
				offset+=2;
				for(int j=0;j<attributes_count;j++){//读attribute_info有问题，应该有三种可能
					//就是读取attribute_info的bug，要考虑6种类型
					long attribute_name_index = read2Bytes();//通过名字判断类型
					offset+=2;
					System.out.println(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index]);
					if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("ConstantValue")){
						readNBytes(6);
						offset+=6;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Synthetic")){
						readNBytes(4);
						offset+=4;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Deprecated")){
						readNBytes(4);
						offset+=4;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Signature")){
						readNBytes(6);
						offset+=6;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("RuntimeVisibleAnnotations")){
						int RuntimeVisibleAnnotations_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(RuntimeVisibleAnnotations_attribute_length);
						offset+=RuntimeVisibleAnnotations_attribute_length;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("RuntimeInvisibleAnnotations")){
						int RuntimeInvisibleAnnotations_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(RuntimeInvisibleAnnotations_attribute_length);
						offset+=RuntimeInvisibleAnnotations_attribute_length;
					}
				}
			}
			System.out.println("方法起始位置: " + offset);
			sum = read2Bytes();//methods_count
			
			List<String> methodNameInOneClass = new ArrayList<String>();
			
			for(int i=0;i<sum;i++){//分析方法
				readNBytes(2);
				long name_index = read2Bytes();
				/*System.out.println("方法 " + (i+1) +"的名字是：" + UTF8_CONSTANT_Utf8_info[(int) name_index] 
						+ " 它的name_index: " + name_index);
				out.write("\t" + UTF8_CONSTANT_Utf8_info[(int) name_index] + "\r\n");*/
				methodNameInOneClass.add(UTF8_CONSTANT_Utf8_info[(int) name_index]);
				
				readNBytes(2);
				long attributes_count = read2Bytes();
				offset+=8;
				for(int j = 0;j<attributes_count;j++){
					long attribute_name_index = read2Bytes();
					offset+=2;
					if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Code")){
						int code_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(code_attribute_length);
						offset+=code_attribute_length;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Exceptions")){
						int exceptions_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(exceptions_attribute_length);
						offset+=exceptions_attribute_length;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Synthetic")){
						readNBytes(4);
						offset+=4;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Signature")){
						readNBytes(6);
						offset+=6;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("Deprecated")){
						readNBytes(4);
						offset+=4;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("RuntimeVisibleAnnotations")){
						int RuntimeVisibleAnnotations_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(RuntimeVisibleAnnotations_attribute_length);
						offset+=RuntimeVisibleAnnotations_attribute_length;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("RuntimeInvisibleAnnotations")){
						int RuntimeInvisibleAnnotations_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(RuntimeInvisibleAnnotations_attribute_length);
						offset+=RuntimeInvisibleAnnotations_attribute_length;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("RuntimeVisibleParameterAnnotations")){
						int RuntimeVisibleParameterAnnotations_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(RuntimeVisibleParameterAnnotations_attribute_length);
						offset+=RuntimeVisibleParameterAnnotations_attribute_length;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("RuntimeInvisibleParameterAnnotations")){
						int RuntimeInvisibleParameterAnnotations_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(RuntimeInvisibleParameterAnnotations_attribute_length);
						offset+=RuntimeInvisibleParameterAnnotations_attribute_length;
					}else if(UTF8_CONSTANT_Utf8_info[(int)attribute_name_index].equals("AnnotationDefault")){
						int AnnotationDefault_attribute_length = read4Bytes();
						offset+=4;
						readNBytes(AnnotationDefault_attribute_length);
						offset+=AnnotationDefault_attribute_length;
					}
				}
			}
			
			String string = file.getAbsolutePath().replaceAll("LXHLXH", "");
			
			allMethodNameInJar.put(string, methodNameInOneClass);
		}catch(Exception e){ 
			
		}
		return sum;
	}
	
	public static long read2Bytes(){ //一次读2个字节
		int idx = 0;
		int times=0;
		try {
			int tempbyte;
			String hex;
			for(times=0;times<2;times++){
				tempbyte=classFile.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
//				idx+=j*Math.pow(256, times);
				idx = idx*256 + j;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return idx;
	}
	
	public static int read4Bytes(){ //一次读四个字节
		int idx = 0;
		int times=0;
		try {
			int tempbyte;
			String hex;
			for(times=0;times<4;times++){
				tempbyte=classFile.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				idx = idx*256 + j;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return idx;
	}
	
	public static String readNBytes(long N){	//一次读N个字节
		int tempbyte;
		String hex;
		String strClassName = new String();
		try {
			for(long times=0;times<N;times++){
				tempbyte = classFile.read();
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
//		return strClassName;
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
	
	private static int hexToInt(String str){
		Integer in = Integer.valueOf(str,16);
		return in;
	}

}
