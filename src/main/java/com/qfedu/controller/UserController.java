/**
 * 用户控制器，用于登录
 *
 * @author 刘鹏尧
 * @date
 */
package com.qfedu.controller;


import com.qfedu.pojo.User;
import com.qfedu.service.IUserService;
import com.qfedu.util.MyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;


/*Spring的组件扫描，如果发现这个注解，就会实例化这个类，然后放入Spring容器*/
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);


    @Autowired
    private IUserService userService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/refuse")
    public String refuse(){

        return "refuse";
    }
    @RequestMapping("/login")
    public String login(User user, Model model, HttpSession session,Boolean rememberMe) { //User user接收前台参数
/*        logger.info(user);
        *//*默认跳转的视图*//*
        String view = "login";

        //根据用户名查询用户信息
        User userLogin = userService.login(user);

        //如果登录成功，则跳转到首页
        if (userLogin != null && user.getPassword().equals(userLogin.getPassword())) {
            session.setAttribute("currentUser",userLogin);
            //view = "redirect:/index"; //此处是转发到viewcontroller中 因为重定向和转发不走视图解析器
            //直接写 "index"也没有报错
            view = "redirect:/index";
        }else{
            String msg="用户名或者密码错误";
            model.addAttribute("msg",msg); //model用于给前端传数据
        }
        return view;*/
        //User userLogin = userService.login(user);
        UsernamePasswordToken usernamePasswordToken;
        if(rememberMe!=null) {
             usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword(), rememberMe);
        }
        else  usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword());

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(usernamePasswordToken);//会执行doGetAuthenticationInfo
        }catch (Exception e){
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }

        return "redirect:/index"; //使url请求地址发生改变 防止无刷新
    }

    @RequestMapping("/logout")
    public String logout(){

        Subject subject = SecurityUtils.getSubject();

        subject.logout();

        return "redirect:/toLogin";
    }


    @RequestMapping("/delete")
    public String delete(){

        return "delete";
    }

    @RequestMapping("/query")
    public String query(){

        return "query";
    }

    @RequestMapping("/add")
    public String add(){

        return "add";
    }

    @RequestMapping("/showRegister")
    public String showRegister(){
        return "showRegister";
    }

    @RequestMapping("/sendCode")
    @ResponseBody
    public String sendCode(String phone){
        if(phone==null){
            return "error";
        }
        //2 存储验证码
        Jedis jedis =new Jedis("192.168.209.128",6379);
//发送不能超过三次
        String num = jedis.get("num"+phone);
        if(num ==null) {
            jedis.setex("num"+phone,3600*24,"3");
        } else if(num != null && (Integer.parseInt(num) > 1)) {
            jedis.decr("num"+phone);
        } else if(jedis.get("num"+phone).equals("1")) {
            return "threetimes";
        }

        String ver = String.valueOf(MyUtils.getRandom());
        String phonekey = "phone_num:"+phone;
        jedis.setex(phonekey,20, ver);  //设置有效时长20s


        jedis.close();
        //3 发送验证码
        System.out.println(ver);
        //4 返回
        return "success";
    }

    @RequestMapping("/verifiCode")
    @ResponseBody
    public String verifiCode(String phone,String verify_code){


        //判断参数
        if(verify_code==null){
            return "error";
        }

        //验证
        Jedis jedis =new Jedis("192.168.209.128",6379);
        String phonekey = jedis.get("phone_num:"+phone);

        System.out.println(phonekey);

        if(verify_code.equals(phonekey)){
            return "success";
        }
        jedis.close();
        return "error";
    }

}
