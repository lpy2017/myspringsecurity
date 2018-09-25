package com.us.example.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@Component("mySecurity")
public class MySecurity {

	public boolean check(Authentication authentication, HttpServletRequest request){
        //如果能获取到Principal对象不为空证明，授权已经通过
        Object principal  = authentication.getPrincipal(); // admin /  anonymity user 
        System.out.println(principal.getClass());
		/***
		自定义判断规则，返回boolean 类型值
		***/
        System.out.println(" principal : "+principal);
        System.out.println(request.getRequestURI());
        if("admin".equals(authentication.getName())||request.getRequestURI().contains("resource")||request.getRequestURI().contains("user")) {
//        if(principal  != null && principal  instanceof UserDetails){
                //获取请求登录的url
//                System.out.println(((UserDetails)principal).getAuthorities()) ;
        	System.out.println("MySecurity  check true");
                return true;
        }
        System.out.println("MySecurity  check false");
        return false;
  }

}
