package com.idanchuang.cms.server.interfaces.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.ContainerRender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lei.liu
 * @date 2021/9/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRenderDTO implements Serializable {
    private static final long serialVersionUID = 4805142426647491623L;

    @ApiModelProperty("页面ID 兼容老版本字段")
    private Integer id;

    @ApiModelProperty("页面ID")
    private Integer pageId;

    @ApiModelProperty("是否有新版本更新。0否；1是")
    private int updateVersion;

    @ApiModelProperty("是否降级。0否；1是")
    private int isDegrade;

    @ApiModelProperty("最新版本号")
    private String newerVersion;

    @ApiModelProperty("平台创建类型")
    private String platformVersion;

    @ApiModelProperty("页面信息")
    private String pageStyle;

    @ApiModelProperty(value = "页面标题")
    private String pageTitle;

    @ApiModelProperty(value = "是否分享 1-是 0-否")
    private Integer shareFlag;

    @ApiModelProperty(value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(value = "分享图片")
    private String shareImage;

    @ApiModelProperty(value = "分享海报")
    private String sharePoster;

    @ApiModelProperty(value = "分享海报url数组")
    private List<String> sharePosterList;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "开始时间，格式yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @ApiModelProperty(value = "结束时间，格式yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;

    @ApiModelProperty(value = "用户等级")
    private Integer userLevel;

    @ApiModelProperty(value = "专题活动状态，0未知；1未开始；2进行中；3已结束")
    private Integer status;

    @ApiModelProperty(value = "服务器时间")
    private Long serverTimestamp;

    @ApiModelProperty("专题会场标识：9积分会场")
    private Integer taskFlag;

    @ApiModelProperty("别名")
    private String aliasTitle;

    @ApiModelProperty("用户ID Code")
    private Integer userIdCode;

    @ApiModelProperty("平台类型")
    private String platform;

    @ApiModelProperty("用户角色")
    private Integer role;

    @ApiModelProperty("商品标签")
    private Long goodsTagId;

    @ApiModelProperty("容器列表")
    private List<ContainerDTO> containers;

    @ApiModelProperty("服务器时间戳")
    private long timestamp = System.currentTimeMillis();


    public static PageRenderDTO degrade() {
        PageRenderDTO version = new PageRenderDTO();
        version.setIsDegrade(1);
        version.setUpdateVersion(1);
        version.setNewerVersion("");
        return version;
    }

    private static ContainerDTO apply(ContainerRender p) {
        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setContainerId(p.getContainerId().getValue());
        containerDTO.setContainerCode(null != p.getContainerCode() ? p.getContainerCode().getValue() : "");
        containerDTO.setStyleContent(p.getStyleContent());
        return containerDTO;
    }

    public void addContainers(List<ContainerRender> containers) {

        if (CollectionUtils.isEmpty(containers)) {
            return;
        }
        this.containers = containers.stream().map(PageRenderDTO::apply).collect(Collectors.toList());
    }

}
