package com.jettech.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jettech.util.TokenUtil;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(AuthHandlerInterceptor.class);

	@Autowired
	TokenUtil tokenUtil;
	@Value("${token.privateKey}")
	private String privateKey;
	@Value("${token.yangToken}")
	private Long yangToken;
	@Value("${token.oldToken}")
	private Long oldToken;

	/**
	 * 权限认证的拦截操作.
	 */
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object) throws Exception {
		log.info("=======进入拦截器========");
		// 如果不是映射到方法直接通过,可以访问资源.
		if (!(object instanceof HandlerMethod)) {
			return true;
		}
		// 为空就返回错误
		String token = httpServletRequest.getHeader("token");
		if (null == token || "".equals(token.trim())) {
			return false;
		}
		log.info("传入的token:" + token);
		Map<String, String> map = tokenUtil.parseToken(token);

		long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
		// 判断 token 是否过期
		if (timeOfUse >= yangToken && timeOfUse < oldToken) {
			return true;
		}
		// 过期 token 就重新设置一个
		else {
			httpServletResponse.setHeader("token", tokenUtil.getToken("PMC_TOKEN"));
			return true;
		}

	}

}
