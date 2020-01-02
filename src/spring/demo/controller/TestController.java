package spring.demo.controller;

import spring.demo.annotation.Autowried;
import spring.demo.annotation.Controller;
import spring.demo.annotation.RequestMapper;
import spring.demo.service.TestService;
import spring.demo.service.UserService;

@Controller
public class TestController {
	
	@Autowried
	private UserService usr;
	
	private TestService tes;
	 
	
	@RequestMapper("test")
	public void test() {
		usr.user();
//		tes.test();
		System.out.println("调用了test方法");
	}
	
}
