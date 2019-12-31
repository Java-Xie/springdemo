package springdemo.controller;

import springdemo.annotation.RequestMapper;

public class testcontroller {
	
	@RequestMapper("test")
	public void test() {
		System.out.println("调用了test方法");
	}
	
	public testcontroller() {
		System.out.println("加载了testcontroller类");
	}
	
}
