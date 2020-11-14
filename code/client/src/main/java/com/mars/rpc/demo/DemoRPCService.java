package com.mars.rpc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Spencer Gibb
 */
public class DemoRPCService {
	@Autowired
	HelloClient client;

	@RequestMapping("/")
	public String hello() {
		return client.hello();
	}


	@FeignClient("HelloServer")
	interface HelloClient {
		@RequestMapping(value = "/", method = GET)
		String hello();
	}
}
