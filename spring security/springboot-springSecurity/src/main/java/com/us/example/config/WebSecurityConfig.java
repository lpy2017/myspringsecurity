package com.us.example.config;

import com.us.example.service.CustomUserService;
import com.us.example.service.MyAccessDecisionManager;
import com.us.example.service.MyAuthenticationSuccessHandle;
import com.us.example.service.MyAuthorticationProvider;
//import com.us.example.service.MyFilterSecurityInterceptor;
import com.us.example.service.UnauthorizedEntryPoint;

//import com.us.example.service.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by yangyibo on 17/1/18.
 */


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider  provider;
//    @Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandle;


    @Bean
    UserDetailsService customUserService() { //注册UserDetailsService 的bean
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider( provider).userDetailsService(customUserService());
//        auth.userDetailsService(customUserService());
        	 //user Details Service验证
    	
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint())
    	.and()
        .formLogin()
        .loginPage("/login")
        .successHandler(myAuthenticationSuccessHandle)
        .failureUrl("/login?error")
        .permitAll() //登录页面用户任意访问
    	.and()
    	
        .authorizeRequests()
			//.antMatchers  有先后顺序，前面有配置的就生效
                .antMatchers("/signup").permitAll()
                .antMatchers("/css/**.*").permitAll()// **包含css 下的多级目录  * 只是该层级
                .anyRequest().access("@mySecurity.check(authentication,request)")
//                .anyRequest().authenticated() //任何请求,登录后可以访问
//                .anyRequest().authenticated().accessDecisionManager(new MyAccessDecisionManager())
                //antMatchers 会导致antMatchers 的配置失效 
                .and()
              /*  .formLogin()
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandle)
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
                .and()*/
                .logout().logoutSuccessUrl("/logout")
                .permitAll()
                .and()
; //注销行为任意访问
//        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
//                .csrf().disable();
        
		/**
		 * 使用accessDecisionManager 自定义的会导致 .antMatchers("/signup").permitAll()
                .antMatchers("/css/**.*").permitAll() 这类配置失效
		 */

    }
}

