package com.sckr.security.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 下拉列表
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "列表")
public class SelectVO implements Serializable {

    @ApiModelProperty(value = "展示信息")
    private String key;

    @ApiModelProperty(value = "信息值")
    private String value;

    private String remark;

    private String pageUrl;

    private String gridUrl;

    private String gridtitleUrl;

    private Boolean checked;

    private List<SelectVO> childList;

    public SelectVO() {
    }

    public SelectVO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public SelectVO(String key, String value, String remark) {
        this.key = key;
        this.value = value;
        this.remark=remark;
    }

    public SelectVO(String key, String value, String remark, Boolean checked) {
        this.key = key;
        this.value = value;
        this.remark=remark;
        this.checked=checked;
    }

    public SelectVO(String key, String value, String pageUrl, String gridUrl, String gridtitleUrl) {
        this.key = key;
        this.value = value;
        this.pageUrl = pageUrl;
        this.gridUrl = gridUrl;
        this.gridtitleUrl = gridtitleUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getGridUrl() {
        return gridUrl;
    }

    public void setGridUrl(String gridUrl) {
        this.gridUrl = gridUrl;
    }

    public String getGridtitleUrl() {
        return gridtitleUrl;
    }

    public void setGridtitleUrl(String gridtitleUrl) {
        this.gridtitleUrl = gridtitleUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<SelectVO> getChildList() {
        return childList;
    }

    public void setChildList(List<SelectVO> childList) {
        this.childList = childList;
    }
}
