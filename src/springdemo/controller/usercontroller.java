package springdemo.controller;

import springdemo.annotation.RequestMapper;

public class usercontroller {
	
	@RequestMapper("user")
	public void user() {
		System.out.println("������user����");
	}
	
	public usercontroller() {
		System.out.println("������usercontroller��");
	}

}
