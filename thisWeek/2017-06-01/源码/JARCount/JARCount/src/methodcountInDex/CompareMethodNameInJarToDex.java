package methodcountInDex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class CompareMethodNameInJarToDex {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {//比较两个文本是否相同
		// TODO Auto-generated method stub
		PythonInterpreter interpreter = new PythonInterpreter(); //调用python程序
		interpreter.execfile("G:\\已测apk\\Test_dx\\类名对比\\compare.py");
		PyFunction func = (PyFunction)interpreter.get("getLength",PyFunction.class);//先获取文件长度
		PyObject pyobj = func.__call__(new PyString("G:/huaweiPro/项目资料/class文件格式/MethodNameInJar.txt"));
		int rowLength = pyobj.asInt();
//		System.out.println(rowLength);
		func = (PyFunction)interpreter.get("equal",PyFunction.class);//再进行对比，内容一致？长度等于原长度？
		pyobj = func.__call__(new PyString("G:/huaweiPro/项目资料/class文件格式/DirectAndVirtualMethodNameInDex.txt"),
				new PyString("G:\\huaweiPro\\项目资料\\class文件格式\\MethodNameInJar.txt"),
				new PyInteger(rowLength));
		String flag = pyobj.toString();
		flag = flag.toUpperCase();
		if(flag.equals("TRUE")){
			System.out.println("success! dex 与 jar包中的direct和virtual方法一一对应");
		}else if(flag.equals("FALSE")){
			System.out.println("FAIL! dex 与 jar包中的方法不对应");
		}
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("F:/allDexClassName.txt")));
		Set<String> set = (Set<String>) ois.readObject();
		List<String> classFromAllMethod = new ArrayList<String>(set);
		ois = new ObjectInputStream(new FileInputStream(new File("F:/partDexClassName.txt")));
		List<String> classFromDriectAndVirtualMethodInDex = (List<String>) ois.readObject();
		File outputFile = new File("G:/huaweiPro/项目资料/class文件格式/多出来的类.txt");
		FileWriter out = new FileWriter(outputFile);
		int j=0;
		for(int i = 0;i<classFromAllMethod.size();i++){
			if(!classFromDriectAndVirtualMethodInDex.contains(classFromAllMethod.get(i))){
				out.write(classFromAllMethod.get(i) + "\r\n");
				j++;
			}
		}
		out.close();
		//判断这个类是否属于基本类库
		interpreter.execfile("G:\\已测apk\\Test_dx\\类名对比\\filter.py");
		func = (PyFunction)interpreter.get("myfilter",PyFunction.class);
		func.__call__(new PyString("G:\\huaweiPro\\项目资料\\class文件格式\\多出来的类.txt"));
	}
}
