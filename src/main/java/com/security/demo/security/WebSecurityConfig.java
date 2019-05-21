package com.security.demo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author xiaobin
 * @Title: WebSecurityConfig
 * @date 2019/05/14
 * @Description:
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(metadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager); // url拦截
                        return o;
                    }
                })
                .and()
//                .formLogin().loginPage("/login_p").loginProcessingUrl("/login")
                // 定义响应的url
                .formLogin().loginProcessingUrl("/user/login")
                .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .and()
                .logout().permitAll()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(deniedHandler);
    }
}
