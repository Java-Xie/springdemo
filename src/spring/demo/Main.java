package spring.demo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import spring.demo.annotation.Autowried;
import spring.demo.annotation.Controller;
import spring.demo.annotation.RequestMapper;
import spring.demo.annotation.Service;
import spring.demo.controller.TestController;
import spring.demo.controller.UserController;
import spring.demo.service.UserService;

public class Main {
	
	public static void main(String[] args) {
		String packageName = "spring.demo";
		Set<Class<?>> classes = new LinkedHashSet<>(64);
		try {
			String pkgDirName = packageName.replace('.', '/');
			Enumeration<URL> urls = Main.class.getClassLoader().getResources(pkgDirName);
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
		
		System.out.println(classes.size());
		
		for (Class<?> csClass : classes) {
			registerClass(csClass,classes);
		}
		
	}
	
	//
	private static void registerClass(Class<?> clazz,Set<Class<?>> classes) {
		
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			for (Class<?> csClass : classes) {
//				System.out.println(csClass);
				if (field.getAnnotation(Autowried.class) != null) {
					if (csClass == field.getType()) {
						try {
							field.set(obj,csClass.newInstance());
							System.out.println("注入成功:"+field);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			try {
				System.out.println(field.get(obj));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(field.getType() == UserService.class);
		}
//		String className = clazz.getName();
		Method[] methods = clazz.getMethods();
		
		TestController test = null;
		
		UserController user = null;
		
		if (obj instanceof TestController) {
			test = (TestController) obj;
		}
		if (obj instanceof UserController) {
			user = (UserController) obj;
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
				findClassesByFile(decode+"/"+file.getName(),pkgDirName+"."+file.getName(),classes);
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
			if (clazz != null && clazz.getAnnotation(Controller.class)!=null || clazz.getAnnotation(Service.class)!=null) {
//				System.out.println(clazz.getName());
				classes.add(clazz);
			}
		}
	}

	
}
