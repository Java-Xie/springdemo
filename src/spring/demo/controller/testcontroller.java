package spring.demo.controller;

import spring.demo.annotation.Autowried;
import spring.demo.annotation.Controller;
import spring.demo.annotation.RequestMapper;
import spring.demo.service.userservice;

@Controller
public class testcontroller {
	
	@Autowried
	private userservice usr;
	
	@RequestMapper("test")
	public void test() {
		System.out.println("调用了test方法");
	}
	
	public testcontroller() {
		System.out.println("加载了testcontroller类");
	}
	
}
