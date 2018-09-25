package com.us.example.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component("myAuthenticationSuccessHandle")
public class MyAuthenticationSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

	 public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
             throws IOException, ServletException {            
       //什么都不做的话，那就直接调用父类的方法
       super.onAuthenticationSuccess(request, response, authentication);  
       
       //这里可以根据实际情况，来确定是跳转到页面或者json格式。
       //如果是返回json格式，那么我们这么写
  /*     Map<String,String> map=new HashMap<>();
       map.put("code", "200");
       map.put("msg", "登录成功");
       ObjectMapper om = new ObjectMapper();
       response.setContentType("application/json;charset=UTF-8");
       response.getWriter().write(om.writeValueAsString(map));
       
       
       //如果是要跳转到某个页面的，比如我们的那个whoim的则
       new DefaultRedirectStrategy().sendRedirect(request, response, "/");*/
       
 }
}
