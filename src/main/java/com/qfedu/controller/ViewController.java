/**
 * 用于项目启动时默认页面跳转
 *
 * @author 刘鹏尧
 * @date
 */

package com.qfedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ViewController {
    @RequestMapping("/")
    public String index1() {
        System.out.println("xxxx");
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }


        @RequestMapping("/hello")
        public String hello(Model model){

            model.addAttribute("hello","hello freemarker!");

            return "hello";
        }

    @RequestMapping("/vue")
    public String vue(Model model){

        model.addAttribute("hello","hello freemarker!");

        return "vue";
    }


}
