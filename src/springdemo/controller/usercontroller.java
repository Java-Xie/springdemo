package springdemo.controller;

import springdemo.annotation.RequestMapper;

public class usercontroller {
	
	@RequestMapper("user")
	public void user() {
		System.out.println("调用了user方法");
	}
	
	public usercontroller() {
		System.out.println("加载了usercontroller类");
	}

}
