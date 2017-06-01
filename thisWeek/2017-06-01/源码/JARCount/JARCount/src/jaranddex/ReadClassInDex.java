package jaranddex;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ReadClassInDex {
	private static int sum =0;
	/**
	 * @param fileName 所有dex文件集中的路径
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		
		File[] allDexFile = file.listFiles();
		sum = 0;//class数量
		for(File temp : allDexFile){
			InputStream in = null;
			int index = -1;
			try {
//				System.out.println("以字节为单位读取文件内容，一次读一个字节：");
				// 一次读一个字节
				in = new FileInputStream(temp);
				int tempbyte;
				String hex;
				for (index = 0; (tempbyte = in.read()) != -1 && index <= 99; index++) {
					if (index < 96) {
						continue;
					}
					hex = Integer.toHexString(tempbyte & 0xFF);
					if (hex.length() == 1) {
						hex = '0' + hex;
					}
					hex = hex.toUpperCase();
					int j = hexToInt(hex);
					sum+=j*Math.pow(256, index-96);
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	/**
	 * @param file 单个的一个dex文件
	 */
	public static void readFileByBytes(File file) {
		sum = 0;// class数量
		InputStream in = null;
		int index = -1;
		try {
			in = new FileInputStream(file);
			int tempbyte;
			String hex;
			for (index = 0; (tempbyte = in.read()) != -1 && index <= 99; index++) {
				if (index < 96) {
					continue;
				}
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				sum += j * Math.pow(256, index - 96);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
			}
		}
	}
	
	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int hexToInt(String str){
		Integer in = Integer.valueOf(str,16);
		return in;
	}
	
	public int getSum(){
		return sum;
	}
}
