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

import springdemo.annotation.RequestMapper;
import springdemo.controller.testcontroller;
import springdemo.controller.usercontroller;

public class main {
	
	public static void main(String[] args) {
		String packageName = "springdemo.controller";
		Set<Class<?>> classes = new LinkedHashSet<>(64);
		try {
			String pkgDirName = packageName.replace('.', '/');
			Enumeration<URL> urls = main.class.getClassLoader().getResources(pkgDirName);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
//				String protocol = url.getProtocol();
				String decode = URLDecoder.decode(url.getFile(), "utf-8");
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
	
	//2��
	//
	private static void registerClass(Class<?> clazz) {
		String className = clazz.getName();
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
		for (Method method : methods) {
			try {
				if (method.getAnnotation(RequestMapper.class) != null) {
					if (test != null) {
						method.invoke(test);
					}
					if (user != null) {
						method.invoke(user);
					}
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

	private static void findClassesByFile(String decode,String pkgDirName,Set<Class<?>> classes) {
		File dir = new File(decode);
		File[] listFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith("class");
			}
		});
		for (File file : listFiles) {
			if (file.isDirectory()) {
				findClassesByFile(pkgDirName+"/"+file.getName(),decode+"."+file.getName(),classes);
				continue;
			}
			String className = file.getName();
			className = pkgDirName + "." + className.substring(0, className.length() - 6);
			
			Class<?> clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (clazz != null) {
				classes.add(clazz);
			}
		}
	}

	
}
