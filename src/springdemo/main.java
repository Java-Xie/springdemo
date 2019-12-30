package springdemo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import springdemo.controller.testcontroller;
import springdemo.controller.usercontroller;

public class main {
	
	public static void main(String[] args) {
		String packageName = "springdemo.controller";
		//保存对象实例的set集合
		Set<Class<?>> classes = new LinkedHashSet<>(64);
		try {
			String pkgDirName = packageName.replace('.', '/');
			Enumeration<URL> urls = main.class.getClassLoader().getResources(pkgDirName);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
//				判断该文件是什么类型的
//				String protocol = url.getProtocol();
				//默认写以文件形式的解析
				//得到包的全路径
				String decode = URLDecoder.decode(url.getFile(), "utf-8");
				//得到包的类实例
				findClassesByFile(decode,packageName,classes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Class<?> controllerClass : classes) {
			registerClass(controllerClass);
		}
		
	}
	
	//2、
	//
	private static void registerClass(Class<?> clazz) {
		//得到类实例的名称
		String className = clazz.getName();
		//得到该类的所有方法
		Method[] methods = clazz.getMethods();
		
		testcontroller test = null;
		
		usercontroller user = null;
		
		try {
			Object obj = clazz.newInstance();
			if (obj instanceof testcontroller) {
				test = (testcontroller) obj;
			}
			if (obj instanceof usercontroller) {
				user = (usercontroller) obj;
			}
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//调用该类的所有方法
		for (Method method : methods) {
			try {
				if (test != null) {
					method.invoke(test);
				}
				if (user != null) {
					method.invoke(user);
				}
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	//1、
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
