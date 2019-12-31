package spring.demo.service;

import spring.demo.annotation.Service;

@Service
public class userservice {
	
	public userservice () {
		System.out.println("加载了userservice类");
	}
	
	public void user () {
		System.out.println("调用了service类中的user方法");
	}
	
}
