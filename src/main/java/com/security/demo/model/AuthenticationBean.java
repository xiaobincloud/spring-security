package com.security.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaobin
 * @Title: AuthenticationBean
 * @date 2019/05/14
 * @Description:
 */
@Data
@NoArgsConstructor
public class AuthenticationBean {

    private String username;
    private String password;
}
