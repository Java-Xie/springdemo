package springdemo.controller;

import springdemo.annotation.RequestMapper;

public class testcontroller {
	
	@RequestMapper("test")
	public void test() {
		System.out.println("������test����");
	}
	
	public testcontroller() {
		System.out.println("������testcontroller��");
	}
	
}
