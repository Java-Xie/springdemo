package spring.demo.controller;

import spring.demo.annotation.Controller;
import spring.demo.annotation.RequestMapper;

@Controller
public class UserController {
	
	@RequestMapper("user")
	public void user() {
		System.out.println("调用了user方法");
	}
	
	public UserController() {
		System.out.println("加载了usercontroller类");
	}

}
