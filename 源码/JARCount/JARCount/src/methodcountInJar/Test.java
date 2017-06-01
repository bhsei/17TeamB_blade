package methodcountInJar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		 int count = 0;
//	        List<String> names = getClassNames("G:/已测apk/Test_dx/jar/classes-dex2jar.jar");
//	        for (String name : names) {
//	            System.out.println(name);
//	            Class<?> clazz = Class.forName(name);
//	            Method[] methods = clazz.getDeclaredMethods();
////	            for(Method m : methods) {
////	                System.out.println(m.getName());  //这里可能会有问题哦
////	                count++;
////	            }
//	            System.out.println("all count: " + methods.length);
//	        }
//		File file = new File("F:/软件工程实验/waitforjar222");
//		FileUtils.deleteDirectory(file);
//	        System.out.println("all count: " + count);
		
//		List<String> fileDir = new ArrayList<String>();
//		fileDir.add("G:/已测apk/Test_dx/ClassInJar/classes-dex2jar/com/tencent/wxop/stat/");
//		fileDir.add("a");
//		fileDir.add("b");
//		fileDir.add("aLXHLXH");
//		fileDir.add("c");
//		String i = untilNotInclude(fileDir,"a");
//		System.out.println(i);
//		for(String str:fileDir){
//			System.out.println(str);
//		}
//		String j = theLongestMatch(fileDir,"aLXHLXHLXHLXH");
//		System.out.println(j);
		File file = new File("G:/已测apk/Test_dx/ClassInJar/Test/1");
		file.mkdirs();
//		File file = new File("G:/已测apk/Test_dx/ClassInJar/classes-dex2jar/com/tencent/wxop/Stat/".substring(0, "G:/已测apk/Test_dx/ClassInJar/classes-dex2jar/com/tencent/wxop/Stat/".lastIndexOf('/')));
//		file.mkdirs();
//		file = new File("G:/已测apk/Test_dx/ClassInJar/classes-dex2jar/com/tencent/wxop/stat");
//		System.out.println(file.isDirectory());
//		String str = "G:/已测apk/Test_dx/ClassInJar/classes-dex2jar/com/tencent/wxop/stat/safssd";
//		System.out.println(str.substring(0, str.lastIndexOf('/')));
//		RandomAccessFile dexFile = new RandomAccessFile(new File("G:/已测apk/newly已测/支付宝/classes.dex"),"r");
//		dexFile.seek(2378697);
//		int methodNameLength = decodeUleb128(dexFile);
//		String methodName = readNBytes(dexFile,methodNameLength);
//		methodName = toStringHex(methodName);//这个类定义的virtual方法名
//		System.out.println(methodName);
		 File sadfafile = new File("D:\\webmagic\\code4craft\\repositorys.txt");
	        if(!sadfafile.getParentFile().exists()){
	        	sadfafile.mkdirs();
	        }
	        System.out.println(sadfafile.getParentFile());
	        sadfafile.createNewFile();
	}
	
	
	private static List<String> getClassNames(String jarPath) throws IOException {
        List<String> classNames = new ArrayList<String>();
        ZipInputStream zip = new ZipInputStream(new FileInputStream(jarPath));
        for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                // This ZipEntry represents a class. Now, what class does it represent?
                String className = entry.getName().replace('/', '.'); // including ".class"
                classNames.add(className.substring(0, className.length() - ".class".length()));
            }
        }
        return classNames;
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
	
	private static String untilNotInclude(List<String> fileDir,String firstOFPath){
		List<String> tempFileDir = new ArrayList<String>();
		for(int i=0;i<fileDir.size();i++){
			String str = fileDir.get(i).toUpperCase(); 
			tempFileDir.add(str);
		}
		String temp = firstOFPath.toUpperCase();
		int count = 0;
		while(tempFileDir.contains(temp)){
			temp = temp + "LXHLXH";
			count++;
		}
		for(int i=0;i<count;i++){
			firstOFPath+="LXHLXH";
		}
		fileDir.add(firstOFPath);
		return firstOFPath;
	}
	
	private static String theLongestMatch(List<String> fileDir,String firstOFPath){
		String answer = new String();
		for(String str:fileDir){
			if(str.contains(firstOFPath)){
				answer = str;
			}
		}
		return answer;
	}
}
