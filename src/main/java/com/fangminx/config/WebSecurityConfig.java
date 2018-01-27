package com.fangminx.config;

import com.fangminx.security.AuthProvider;
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
                .and();

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
}
