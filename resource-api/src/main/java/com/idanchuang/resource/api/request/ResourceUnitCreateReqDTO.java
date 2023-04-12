package com.idanchuang.resource.api.request;

import com.idanchuang.resource.api.common.ResourceStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author wengbinbin
 * @date 2021/3/12
 */

@Data
@Accessors(chain = true)
public class ResourceUnitCreateReqDTO implements Serializable {
    @ApiModelProperty(value = "资源位id")
    @NotNull(message = "资源位id不能为空")
    private Long resourceId;

    @ApiModelProperty(value = "投放平台，默认1、1.客户端、2.h5、3.小程序")
    @NotNull(message = "投放平台不能为空")
    private String platformFrom;

    @ApiModelProperty(value = "投放优先级，默认最低等级1")
    @NotNull(message = "投放优先级不能为空")
    private Integer weight;

    @ApiModelProperty(value = "投放起始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "投放结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "投放角色")
    @NotNull(message = "投放角色不能为空")
    private List<Integer> visibleRoleS;

    @ApiModelProperty(value = "投放内容名称")
    @NotNull(message = "投放内容名称不能为空")
    private String contentTitle;

    @ApiModelProperty(value = "组件json内容")
    @NotNull(message = "组件json内容不能为空")
    private String componentJsonData;

    @ApiModelProperty("操作者")
    @NotNull(message = "操作者不能为空")
    private String operatorUser;

    @ApiModelProperty("操作者id")
    @NotNull(message = "操作者id不能为空")
    private Integer operatorId;

    @ApiModelProperty("启用状态，0关闭，1启动")
    private Integer activeStatus;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;

    @ApiModelProperty(value = "组件名称")
    private String componentName;

    public void checkBeginAndEndTime() {
        if (this.weight.equals(-1)) {
            this.startTime = LocalDateTime.now();
            this.endTime = LocalDateTime.parse("2099-01-01T00:00:00");
            this.activeStatus = ResourceStatusEnum.VALID.getStatus();
        }else {
            Assert.notNull(this.startTime,"投放开始时间不能为空");
            Assert.notNull(this.endTime,"投放开始时间不能为空");
        }
    }
}
