package com.sckr.security.controller;

import com.sckr.security.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoukebo
 * @date 2018/9/4
 */
@Api(value = "helloController", tags = {"测试Controller"})
@RestController
public class HelloController {

    @GetMapping("/user")
    @ApiOperation(value="登录", notes="登录接口", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result login(String name) {
        System.out.println("欢迎使用springsecurity");
        return new Result("登录页", "欢迎"+name+"进入security的世界", "拥有ADMIN权限访问");
    }

    @PostMapping("/hello")
    //使用此注解会调用自定义权限验证逻辑，此处的参数会作为第二个和第三个参数传入
//    @PreAuthorize("hasPermission('/hello', 'r')")
    @ApiOperation(value="hello", notes="hello接口", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result hello(@RequestBody String name) {
        return new Result("欢迎页", "欢迎"+name+"进入security的世界", "拥有hello权限访问");
    }

    @PostMapping(value = "/add")
    @ApiOperation(value="添加", notes="添加接口", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result add() {
        return new Result("添加页", "添加内容", "额外信息，只对管理员显示");
    }

    @PostMapping(value = "/home")
    @ApiOperation(value="主页", notes="主页接口", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result home() {
        return new Result("主页", "主页", "额外信息，只对管理员显示");
    }

    @GetMapping("/login")
    public Result login(){
        return new Result("登录提示", "请登录", "请登录");
    }


    @PostMapping(value = "/getUserName")
    @ApiOperation(value="获取用户名", notes="用户名", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
           return new Result("获取用户名称", "用户名称", ((UserDetails) principal).getUsername());
        }
        return new Result();
    }
}
