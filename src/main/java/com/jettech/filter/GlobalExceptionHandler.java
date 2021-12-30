package com.jettech.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jettech.exception.TokenAuthExpiredException;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 用户 token 过期
	 * @return
	 */
	@ExceptionHandler(value = TokenAuthExpiredException.class)
	@ResponseBody
	public String tokenExpiredExceptionHandler() {
		log.warn(" token 已过期");
		return " token 已过期";
	}
}
