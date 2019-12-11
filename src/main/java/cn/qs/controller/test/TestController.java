package cn.qs.controller.test;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.qs.utils.JSONResultUtil;
import cn.qs.utils.web.WebUtils;

@RequestMapping("/test")
@RestController
public class TestController {

	@GetMapping("/get")
	public Map<String, Object> get(@RequestParam Map<String, Object> condition) {
		if (MapUtils.isEmpty(condition)) {
			condition = new LinkedHashMap<>();
			condition.put("param", null);
		}

		return condition;
	}

	@PostMapping("/getJSON")
	public String getJSON(@RequestBody String param) {
		System.out.println(param);
		return param;
	}

	@GetMapping("/getCookie")
	public String getCookie(@CookieValue(value = "cookie1", required = false) String cookie,
			HttpServletRequest request) {

		String header = request.getHeader("X-HEADER1");
		System.out.println(header);

		return cookie;
	}

	@GetMapping("/setCookie")
	public String setCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie2 = new Cookie("cookie1", "value1");
		cookie2.setPath("/");
		response.addCookie(cookie2);

		String cookie = "cookie1=value1";
		return cookie;
	}

	@GetMapping("/getHeader")
	public JSONResultUtil<String> getHeader(@RequestHeader("x-header1") String header1,
			@RequestHeader("x-header2") String header2) {

		System.out.println(header1 + " " + header2);
		return new JSONResultUtil(true, header1 + " " + header2);
	}

}