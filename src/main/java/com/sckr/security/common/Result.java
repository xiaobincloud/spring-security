package com.sckr.security.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端返回的结果
 * @author zhoukebo
 * @date 2018/9/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String title;

    private String content;

    private String extraInfo;
}
