package methodcountInDex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

import methodcountInJar.BatchUnZipper;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Set<String> set = new HashSet<String>();
//		String str1 = "asfasfdsf";
//		String str2 = "vg;jdldskgj";
//		String str3 = "基础";
//		String str4 = "aa";
//		set.add(str1);
//		set.add(str2);
//		set.add(str3);
//		set.add(str4);
//		List<String> list = new ArrayList<String>(set);
//		Collections.sort(list);
//		System.out.println(list);
		
		String className = "[Lcom/tencent/wxop/stat/StatReportStrategy;";
		int end = className.lastIndexOf(";");
		if(end==-1){//基本类型
			className = "基本类型：" + className;
		}else if(className.charAt(0)=='L'){//引用类型
			className = className.substring(0,end);
			className+=".class";
		}else{
			className = "其他引用类型：" + className;
		}
		System.out.println(className.contains("其他引用类型："));
		
//		Map allMethodsName = new HashMap<String,List<String> >();
//		allMethodsName.put("saf",new ArrayList<String>());
//		allMethodsName.put("123",new ArrayList<String>());
//		allMethodsName.put("345",new ArrayList<String>());
//		List list = (List) allMethodsName.get("asff");
//		System.out.println(allMethodsName.containsKey("asff"));
//		System.out.println(list);
		
		/*RandomAccessFile dexFile = null;
		File file = new File("G:/已测apk/Test_dx/dex");
		File[] allDexFile = file.listFiles(); 
		for(File temp : allDexFile){
			dexFile = new RandomAccessFile(temp,"r");
			dexFile.seek(60);
			dexFile.readUnsignedShort();
			dexFile.readUnsignedShort();
			int name_idx = read2Bytes(dexFile);//方法名
			System.out.println(name_idx);
		}*/
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
    
    public static int decodeUleb128(RandomAccessFile dexFile) throws IOException{
    	Byte tempbyte;
    	String str;
    	String result;
    	char c;
    	int sum = 0;
    	int len = 1;
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
    		len++;
    	}
    	int pow = result.length()-1;
    	for(int i=0;i<result.length();i++){
    		sum+=(result.charAt(i)-'0')*Math.pow(2, pow);
    		pow--;
    	}
    	System.out.println(len);
    	return sum;
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
}
