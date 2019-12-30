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
			//�������ʵ����set����
			Set<Class<?>> classes = new LinkedHashSet<>(64);
			String pkgDirName = packageName.replace('.', '/');
			Enumeration<URL> urls = main.class.getClassLoader().getResources(pkgDirName);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
//				�жϸ��ļ���ʲô���͵�
//				String protocol = url.getProtocol();
				//Ĭ��д���ļ���ʽ�Ľ���
				//�õ�����ȫ·��
				String decode = URLDecoder.decode(url.getFile(), "utf-8");
				//�õ���������·��
				findClassesByFile(decode,packageName,classes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//����controller�µ����ж���ʵ��
	private static void findClassesByFile(String decode,String pkgDirName,Set<Class<?>> classes) {
		//ͨ������·���ҵ����ļ�
		File dir = new File(decode);
		//���˷�Ŀ¼��.class�ļ�
		File[] listFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith("class");
			}
		});
		for (File file : listFiles) {
			//���ļ�ΪĿ¼��ݹ�÷���
			if (file.isDirectory()) {
				findClassesByFile(pkgDirName+"/"+file.getName(),decode+"."+file.getName(),classes);
				continue;
			}
			//��ȡ������ȥ��.class��׺
			String className = file.getName();
			className = pkgDirName + "." + className.substring(0, className.length() - 6);
			
			//������
			Class<?> clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//�Ѷ��󱣴浽linkedHashSet
			if (clazz != null) {
				classes.add(clazz);
			}
		}
	}

	
}
