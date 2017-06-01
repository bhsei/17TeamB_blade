package jaranddex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadClassOffsetInDex {
	private static int offset =0;
	/**
	 * @param file 单个的一个dex文件
	 * 读取ClassOffset
	 */
	public static void readOffsetByBytes(File file) {
		offset = 0;// 偏移字节（十进制）
		InputStream in = null;
		int index = -1;
		try {
			in = new FileInputStream(file);
			int tempbyte;
			String hex;
			for (index = 0; (tempbyte = in.read()) != -1 && index <= 103; index++) {
				if (index < 100) {
					continue;
				}
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				offset+=j*Math.pow(256, index-100);
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
	
	private static int hexToInt(String str){
		Integer in = Integer.valueOf(str,16);
		return in;
	}
	
	public static int getOffset(){
		return offset;
	}
}
