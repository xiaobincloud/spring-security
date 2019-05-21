package com.sckr.security.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaobin
 * @Title: LoginVO
 * @date 2019/05/20
 * @Description:
 */
@Data
@NoArgsConstructor
public class LoginVO implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
}
