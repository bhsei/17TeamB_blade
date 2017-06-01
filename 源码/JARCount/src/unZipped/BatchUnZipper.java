package unZipped;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BatchUnZipper {
	private static int sum = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// File zipFile = new File("G:/已测apk/Test_dx/lingshengduoduo/momo.zip");
//		 String path = "d:/zipfile/";
		// unZipFiles(zipFile, path);
		String path = "G:/已测apk/";
		File file = new File("G:/已测apk/Test_dx/apk-zip");
		File[] allFile = file.listFiles();
		File[] allZipFile = new File[allFile.length];// 从所有文件中筛选出zip
		int j = 0;
		for (int i = 0; i < allFile.length; i++) {
			if (allFile[i].getName().endsWith("zip")) {
				allZipFile[j++] = allFile[i];
			}
		}
		sum = 0;// 已解压的zip数量
		for (File temp : allZipFile) {
			unZipFiles(temp, path);
			sum++;
		}
		System.out.println(sum);
	}

	/**
	 * 解压到指定目录
	 * 
	 * @param zipPath
	 * @param descDir
	 * @author isea533
	 */
	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		String name = zipFile.getName();
		name = name.substring(0, name.lastIndexOf('.'));
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
//			System.out.println(outPath);

			OutputStream out = null;
			try{
				out = new FileOutputStream(outPath);//解压中某个小文件出错时，继续解压
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
			}catch(Exception e){
				
			}finally{
				in.close();
				out=null;
			}
//			OutputStream out = new FileOutputStream(outPath);
//			byte[] buf1 = new byte[1024];
//			int len;
//			while ((len = in.read(buf1)) > 0) {
//				out.write(buf1, 0, len);
//			}
//			in.close();
//			out.close();
		}
	}
}
