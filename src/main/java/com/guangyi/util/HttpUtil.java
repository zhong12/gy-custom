package com.guangyi.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhongjing
 * Http 操作类
 */
public class HttpUtil {

	/**
	 * 获取Cookie方法
	 * @param request request对象
	 * @param name Cookie名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if ( cookies != null ) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 添加Cookie方法
	 * @param response response对象
	 * @param name Cookie名称
	 * @param value 值
	 */
	public static void addCookie(HttpServletResponse response, String name, String value) {
		addCookie(response, name, value, 1 * 365 * 24 * 60 * 60);
	}

	/**
	 * 添加Cookie方法
	 * @param response response对象
	 * @param name Cookie名称
	 * @param value 值
	 * @param maxAge 最长存活时间（秒）
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		addCookie(response, name, value, maxAge, "/");
	}

	/**
	 * 添加Cookie方法
	 * @param response response对象
	 * @param name Cookie名称
	 * @param value 值
	 * @param maxAge 最长存活时间（秒）
	 * @param path 存放路径
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, null, -1);
	}
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!CheckUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!CheckUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
}
