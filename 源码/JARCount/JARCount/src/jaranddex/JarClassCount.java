package jaranddex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  
import java.net.JarURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.util.ArrayList;  
import java.util.Enumeration;  
import java.util.List;
import java.util.Map.Entry;
import java.util.jar.JarEntry;  
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils; 

public class JarClassCount {
	
private static ReadClassInDex readClassInDex = new ReadClassInDex();
private static FileWriter out = null;
private static List<String> allClassNames = new ArrayList<>();

	public static void main(String[] args)  throws IOException{
		// TODO Auto-generated method stub
		
		readClassInDex.readFileByBytes("G:/已测apk/Test_dx/dex");
		int class_dex = readClassInDex.getSum();
		System.out.println("dex中class数量"+class_dex);
		
		int class_jar = getSpringClassNumber(); //另一个方法
		System.out.println("jar包中class数量"+class_jar);
		if(class_dex==class_jar){
			System.out.println("dex与jar中class数量一致,数目是：" + class_jar);
		}else{
			System.out.println("dex与jar中class数量不同");
		}
		
		FileUtils.writeLines(new File("F:/temp2.txt"), allClassNames);
		
//		JarFile jf = null;
//		jf = new JarFile("G:/已测apk/Test_dx/app-debug/jar/classes-dex2jar.jar");
//		Enumeration enu = jf.entries();
//		int i = 0;
//		while(enu.hasMoreElements()){
//			JarEntry element = (JarEntry) enu.nextElement();
//			String name = element.getName();
//			if(name.toUpperCase().endsWith(".CLASS")){
////		        System.out.println(name);   
//				i++;
//		    }
//		}
//		System.out.printf( "jar包里包含总的class文件数是" + i );
	}
	
	private static int getSpringClassNumber() throws IOException  
    {  
        File springJar = new File( "G:/已测apk/Test_dx/jar" );
//		File springJar = new File( "G:/已测apk/Test_dx/mojitianqi" ); 
        List list = new ArrayList<URL>();  
          
        File[] allSpringJar = springJar.listFiles();  
        int result = 0;  
        
        File outputFile = new File("G:/已测apk/Test_dx/类名对比/jar.txt");  //存放数组数据的文件 
        try {
			out = new FileWriter(outputFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  //文件写入流 
        for(File temp : allSpringJar)  
        {   
            String urlName = "jar: file:";  
            String name = temp.toString();  
            name.replace( "\\", "//" );  
              
            urlName += name;  
            urlName += "!/";  
//            System.out.println( urlName );  
            
            name = name.replaceAll(".zip", "");
            result += classNumberPerJarFile( urlName,name ) ;  
        }  
//        System.out.printf( "jar包里包含总的class文件数是" + result );
        out.close();
        return result;  
    }  
	 private static int classNumberPerJarFile(String urlName,String name) throws IOException  
	    {  
	        URL url = new URL( urlName );  
	        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();  
	        JarFile jarFile = jarURLConnection.getJarFile();  
	          
	        Enumeration<JarEntry> jarEntries = jarFile.entries();  
	        int i = 0;  
	        while(jarEntries.hasMoreElements())  
	        {  
	            JarEntry jarEntry = jarEntries.nextElement();  
	            if(jarEntry.getName().endsWith( ".class" ))  
	            {  
	            	int start = jarEntry.getName().lastIndexOf("/");
	            	out.write(jarEntry.getName().substring(start+1)+"\r\n");//精简类名 
	                i++;
	                allClassNames.add(name +'\\' + jarEntry.getName().replace('/', '\\'));
	                
	            }  
	              
	        }  
	        return i;  
	          
	    }  
}
