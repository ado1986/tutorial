package org.ado.jetty.tutorial.controller;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.ado.jetty.tutorial.resource.Resources;
import org.ado.jetty.tutorial.response.Response;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private Resources resources;

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

	/**
	 * 异步调用
	 * 
	 * 测试URL：http://localhost:8080/jetty/asyncCall
	 * 
	 * @return
	 */
	@RequestMapping(value = "/asyncCall", method = RequestMethod.GET)
	@ResponseBody
	private Callable<Response> asyncCall() {
		return new Callable<Response>() {
			@Override
			public Response call() throws Exception {
				Response response = new Response();
				response.setStatus(200);
				String ret = resources.getResource();
				response.setMessage(ret);

				return response;
			}
		};
	}
}
