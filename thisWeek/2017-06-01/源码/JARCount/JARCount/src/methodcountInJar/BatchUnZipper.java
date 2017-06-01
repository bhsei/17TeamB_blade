package methodcountInJar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BatchUnZipper {
	private static int sum = 0;
	private static List<String> fileName = null;
	private static List<String> fileDir = null;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		fileName =  new ArrayList<String>();
		String path = "G:/已测apk/Test_dx/ClassInJar/";
		File file = new File("G:/已测apk/Test_dx/jar");
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
			fileDir = new ArrayList<String>();
			unZipFiles(temp, path);
			sum++;
		}
		System.out.println("已解压的压缩文件:	"+sum);
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
			
			if(zipEntryName.endsWith(".class")){
				String fileName_UpperCase = zipEntryName.toUpperCase();
				boolean flag = false;
				for(int i=0;i<fileName.size();i++){
					if(fileName_UpperCase.equals(fileName.get(i))){
						flag = true;
						break;
					}
				}
				/*if(flag==false){//没有大小写敏感文件
					fileName.add(fileName_UpperCase);
				}else{
					int start = zipEntryName.lastIndexOf('/');
					int end = zipEntryName.lastIndexOf('.');
					String str = zipEntryName.substring(start+1, end);
					str = str + "LXHLXHLXH";
					String pre = zipEntryName.substring(0, start+1);
					zipEntryName = pre+str+".class";
					fileName_UpperCase = zipEntryName.toUpperCase();
					fileName.add(fileName_UpperCase);
				}*/
				int start = zipEntryName.lastIndexOf('/');
				int end = zipEntryName.lastIndexOf('.');
				String str = zipEntryName.substring(start+1, end);
				if(str.toUpperCase().equals("AUX")||str.toUpperCase().equals("CON")||str.toUpperCase().equals("NUL")
						||str.toUpperCase().equals("PRN")||str.toUpperCase().equals("COM1")||str.toUpperCase().equals("COM2")
						||str.toUpperCase().equals("COM3")||str.toUpperCase().equals("COM4")||str.toUpperCase().equals("COM5")
						||str.toUpperCase().equals("COM6")||str.toUpperCase().equals("COM7")||str.toUpperCase().equals("COM8")
						||str.toUpperCase().equals("COM9")||str.toUpperCase().equals("LPT1")||str.toUpperCase().equals("LPT2")
						||str.toUpperCase().equals("LPT3")||str.toUpperCase().equals("LPT4")||str.toUpperCase().equals("LPT5")
						||str.toUpperCase().equals("LPT6")||str.toUpperCase().equals("LPT7")||str.toUpperCase().equals("LPT8")
						||str.toUpperCase().equals("LPT9")){//与保留文件名一致会出错
					str = str + "LXHLXH";
					String pre = zipEntryName.substring(0, start+1);
					zipEntryName = pre+str+".class";
					fileName_UpperCase = zipEntryName.toUpperCase();
					while(fileName.contains(fileName_UpperCase)){
						str = str + "LXHLXH";
						pre = zipEntryName.substring(0, start+1);
						zipEntryName = pre+str+".class";
						fileName_UpperCase = zipEntryName.toUpperCase();
					}
				}
				if(flag){
					/*str = str + "LXHLXH";
					String pre = zipEntryName.substring(0, start+1);
					zipEntryName = pre+str+".class";
					fileName_UpperCase = zipEntryName.toUpperCase();*/
					while(fileName.contains(fileName_UpperCase)){
						str = str + "LXHLXH";
						String pre = zipEntryName.substring(0, start+1);
						zipEntryName = pre+str+".class";
						fileName_UpperCase = zipEntryName.toUpperCase();
					}
				}
				fileName.add(fileName_UpperCase);
			}
			
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
				fileDir.add(outPath.substring(0, outPath.lastIndexOf('/')));
			}else{
				if(!fileDir.contains(outPath.substring(0, outPath.lastIndexOf('/')))){//Windows路径名不分大小写
					String firstOFPath = outPath.substring(0, outPath.lastIndexOf('/')) + "LXHLXH";
					fileDir.add(firstOFPath);
					boolean isFirstTime = showTimes(fileDir,firstOFPath);
					if(isFirstTime){//第一次出现，新目录
						firstOFPath = untilNotInclude(fileDir,firstOFPath);
						firstOFPath = theLongestMatch(fileDir,firstOFPath);
					}else{//旧目录
						firstOFPath = theLongestMatch(fileDir,firstOFPath);
					}
					String lastOFPath = outPath.substring(outPath.lastIndexOf('/'));
					outPath = firstOFPath + lastOFPath;
					file = new File(firstOFPath);
					if(!file.exists())/////////
						file.mkdirs();
				}
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
//				System.out.println(outPath);
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
		}
		zip.close();
	}
	
	/**
	 * 
	 * @param fileDir
	 * @param firstOFPath
	 * 直到大写不包括,其实可以用equalsIgnoreCases
	 * @return
	 */
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
	
	/**
	 * 
	 * @param firstOFPath
	 * @return
	 * 找到最长匹配
	 */
	private static String theLongestMatch(List<String> fileDir,String firstOFPath){
		String answer = new String();
		for(String str:fileDir){
			if(str.contains(firstOFPath)){
				answer = str;
			}
		}
		return answer;
	}
	
	private static boolean showTimes(List<String> fileDir,String firstOFPath){
		boolean isFirstTime = true;
		int times = 0;
		for(String str:fileDir){
			if(str.equals(firstOFPath)){
				times++;
			}
		}
		if(times>1){
			isFirstTime = false;
		}
		return isFirstTime;
	}
}
