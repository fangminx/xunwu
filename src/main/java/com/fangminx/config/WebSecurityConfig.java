package com.fangminx.config;

import com.fangminx.security.AuthProvider;
import com.fangminx.security.LoginAuthFailHandler;
import com.fangminx.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


    /**
     * HTTP权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //资源访问权限
        http.authorizeRequests()
                .antMatchers("/admin/login").permitAll() //管理员登录入口
                .antMatchers("/static/**").permitAll() //静态资源
                .antMatchers("/user/login").permitAll() //用户登录入口
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/api/user/**").hasAnyRole("ADMIN","USER")
                .and()
                .formLogin()
                .loginProcessingUrl("/login") //配置角色登录处理入口
                .failureHandler(authFailHandler()) //登录失败处理
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout/page")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true) //使session会话失效
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(urlEntryPoint())
                .accessDeniedPage("/403");//无权访问的提示页面

        http.csrf().disable(); //防御策略
        http.headers().frameOptions().sameOrigin(); //h-ui 需要使用同源策略
    }

    /**
     * 自定义认证策略
     * 在@EnableGlobalAuthentication注解下，只能有一个AuthenticationManagerBuilder注入
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        //内存配置
//        auth.inMemoryAuthentication().withUser("admin").password("admin")
//                .roles("ADMIN").and();

        auth.authenticationProvider(authProvider()).eraseCredentials(true);
    }

    // 注入自定义认证实现
    @Bean
    public AuthProvider authProvider(){
        return new AuthProvider();
    }

    // 注入基于角色的登录入口控制器
    @Bean
    public LoginUrlEntryPoint urlEntryPoint(){
        return new LoginUrlEntryPoint("/user/login");
    }

    // 注入登录失败处理器
    @Bean
    public LoginAuthFailHandler authFailHandler() {
        return new LoginAuthFailHandler(urlEntryPoint());
    }
}
