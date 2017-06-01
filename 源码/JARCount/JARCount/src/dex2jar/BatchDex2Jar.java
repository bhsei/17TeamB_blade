package dex2jar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

public class BatchDex2Jar {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File toolDir = new File("G:/huaweiPro/dex2jar-2.x/dex-tools/build/distributions/dex-tools-2.1-SNAPSHOT");
		
		File file = new File("G:/已测apk/newly已测");
		File[] allFile = file.listFiles();
		
		for(File dir:allFile){//dir是一个子文件夹
			deleteFile(toolDir);//删除dex2jar目录下的dex和jar
			File judgeFile = new File("F:/软件工程实验/waitforjar");
			if(judgeFile.exists()){
				FileUtils.deleteDirectory(judgeFile);
			}
			File[] fileInDir = dir.listFiles();
			File[] dexFile = new File[fileInDir.length];
			int i = 0;
			for(File temp:fileInDir){
				if(temp.getName().endsWith(".dex")){
					//copy
					dexFile[i++] = temp;
					FileUtils.copyFileToDirectory(temp, toolDir);
				}
			}
			//调用dex2jar.bat
			runbat("G:/eclipseproject/JARCount/dex2jar.bat",dexFile);
			//要等他执行完毕否则目录中还没有转好的jar包呢
			waitForJar(toolDir,i);
			File[] fileInToolDir = toolDir.listFiles();
			for(File temp:fileInToolDir){
				if(temp.getName().endsWith(".jar")){
					FileUtils.copyFileToDirectory(temp, dir);
				}
			}
		}
	}
	
	private static void deleteFile(File file){
		File[] toolFile = file.listFiles();
		for(File temp:toolFile){
			if(temp.getName().endsWith(".dex") ||temp.getName().endsWith(".jar")){
				temp.delete();
			}
		}
	}
	private static void runbat(String batName,File[] dexFile) {
		String cmd = "cmd /c start /b " + batName + " ";
		
		Process ps = null;
		 try {
			ps = Runtime.getRuntime().exec(cmd);
			ps.waitFor();
			ps.destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void waitForJar(File file,int size){
		File judgeFile = new File("F:/软件工程实验/waitforjar");
		while(true){
			int count = 0;
			File[] fileInToolDir = file.listFiles();
			for(File temp:fileInToolDir){
				if(temp.getName().endsWith(".jar")){
					count++;
				}
			}
			if(count==size && judgeFile.exists()){
				break;
			}
		}
	}
}
