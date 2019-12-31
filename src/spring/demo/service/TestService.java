package spring.demo.service;

import spring.demo.annotation.Service;

@Service
public class TestService {
	
	public TestService () {
		System.out.println("加载了testservice类");
	}
	
	public void test() {
		System.out.println("调用了services中的test方法");
	}
	
}
