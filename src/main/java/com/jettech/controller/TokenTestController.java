package com.jettech.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jettech.util.TokenUtil;

@RestController
public class TokenTestController {
	private static final String KEY_WORD = "PMC_TOKEN";
	@Autowired
	TokenUtil tokenUtil;

	/**
	 * 使用 /login 请求获得 token, /login 不经过拦截器
	 */
	@RequestMapping("/login")
	public String login() {
		return tokenUtil.getToken("PMC_TOKEN2");
	}

	/**
	 * 使用 /test-token 测试 token，进过拦截器
	 */
	@RequestMapping("/test-token")
	public String testToken(HttpServletRequest request) {
		String token = request.getHeader("token");
		Map<String, String> map = tokenUtil.parseToken(token);
		String keyWord = map.get("keyWord");
		if (!keyWord.equals("PMC_TOKEN")) {
			return "token无效";
		}
		return "认证成功";
	}

}
