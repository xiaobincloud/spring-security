package com.sckr.security.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value = "资源树")
public class SourceTreeVO  implements GrantedAuthority {

    @ApiModelProperty(value = "资源名称")
    private String name;
    @ApiModelProperty(value = "资源路径")
    private String path;
    @ApiModelProperty(value = "子集资源")
    private List<SourceTreeVO> childList;

    public SourceTreeVO(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return path;
    }
}
