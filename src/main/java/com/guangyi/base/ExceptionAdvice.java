package com.guangyi.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * Created by henry on 4/2/14.
 */
@ControllerAdvice
public class ExceptionAdvice {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws IOException, ParseException {
		System.out.println("==================in");

		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		String exceptionTrace =  writer.toString();
		pw.close();
		writer.close();

		System.out.println(exceptionTrace);

		ModelAndView mav = new ModelAndView();
		if (e instanceof org.apache.shiro.authz.UnauthorizedException) {
			mav.setViewName("/error403");
		} else {
			mav.setViewName("/error500");
		}
		mav.addObject("errorMessage", e.getMessage());
		mav.addObject("success", "false");
		return mav;
	}

}
