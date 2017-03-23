package com.guangyi.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhongjing on 2016/05/12 0012.
 */
@Controller
public class HomeController {
    @RequestMapping("/index")
    public String index() {
        return "redirect:home";
    }


    @RequestMapping("/home")
    public String home() {
        System.out.println("123123");
        return "redirect:codes.html";
    }
}
