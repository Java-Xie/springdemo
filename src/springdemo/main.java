package springdemo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class main {
	
	public static void main(String[] args) {
		try {
			String packageName = "springdemo.controller";
			//保存对象实例的set集合
			Set<Class<?>> classes = new LinkedHashSet<>(64);
			String pkgDirName = packageName.replace('.', '/');
			Enumeration<URL> urls = main.class.getClassLoader().getResources(pkgDirName);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
//				判断该文件是什么类型的
//				String protocol = url.getProtocol();
				//默认写以文件形式的解析
				//得到包的全路径
				String decode = URLDecoder.decode(url.getFile(), "utf-8");
				//得到包的物理路径
				findClassesByFile(decode,packageName,classes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//创建controller下的所有对象实例
	private static void findClassesByFile(String decode,String pkgDirName,Set<Class<?>> classes) {
		//通过包的路径找到包文件
		File dir = new File(decode);
		//过滤非目录和.class文件
		File[] listFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith("class");
			}
		});
		for (File file : listFiles) {
			//该文件为目录则递归该方法
			if (file.isDirectory()) {
				findClassesByFile(pkgDirName+"/"+file.getName(),decode+"."+file.getName(),classes);
				continue;
			}
			//获取类名，去掉.class后缀
			String className = file.getName();
			className = pkgDirName + "." + className.substring(0, className.length() - 6);
			
			//加载类
			Class<?> clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//把对象保存到linkedHashSet
			if (clazz != null) {
				classes.add(clazz);
			}
		}
	}

	
}
