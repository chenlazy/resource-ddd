package com.idanchuang.cms.server.interfaces.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVersionDiffForm {


    @ApiModelProperty(value = "pageCode 客户端查询必传，专题页不能传")
    private String pageCode;

    @ApiModelProperty(value = "当前版本")
    private String currentVersion;

    @ApiModelProperty("专题页面别名")
    private String aliasTitle;

    public Integer aliasTitleToId(String aliasTitle) {

        if (StringUtils.isEmpty(aliasTitle)) {
            return null;
        }
        if (aliasTitle.contains("_")) {
            aliasTitle = aliasTitle.substring(aliasTitle.indexOf("_") + 1);
        }
        return NumberUtils.isCreatable(aliasTitle) ? Integer.parseInt(aliasTitle) : null;
    }
}
