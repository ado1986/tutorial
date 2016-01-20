package org.ado.jetty.tutorial.controller;

import org.ado.jetty.tutorial.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring mvc 请求处理类
 * 
 * @author ado1986
 *
 * @create_time 2016年1月20日 上午10:14:17
 */
@Controller
@RequestMapping("/jetty")
public class HelloWorldController {

	/**
	 * 测试URL：http://localhost:8080/jetty/helloWorld
	 * 
	 * @return
	 */
	@RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
	@ResponseBody
	private Response hellWorld() {
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Hello World");

		return response;
	}
}
