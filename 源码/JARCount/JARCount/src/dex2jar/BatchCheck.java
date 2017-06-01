package dex2jar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BatchCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> sourceName = new ArrayList<String>();
		List<String> destName = new ArrayList<String>();
		File sourceDir = new File("G:/已测apk/newly已测");
		File[] sourceFile = sourceDir.listFiles();
		for(File file:sourceFile){
			sourceName.add(file.getName());
		}
		File destDir = new File("G:/huaweiPro/项目资料/提交文档");
		File[] destFile = destDir.listFiles();
		for(File file:destFile){
			destName.add(file.getName());
		}
		for(String str:sourceName){
			if(!destName.contains(str)){
				System.out.println(str);
			}
		}
	}

}
