package com.fangminx.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录验证失败处理器
 */
@AllArgsConstructor
public class LoginAuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private final LoginUrlEntryPoint urlEntryPoint;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //获取登录的url
       String targetUrl = this.urlEntryPoint.determineUrlToUseForThisRequest(request,response,exception);
       targetUrl += "?" + exception.getMessage();  // ?authError
       super.setDefaultFailureUrl(targetUrl);
       super.onAuthenticationFailure(request,response,exception);
    }
}
