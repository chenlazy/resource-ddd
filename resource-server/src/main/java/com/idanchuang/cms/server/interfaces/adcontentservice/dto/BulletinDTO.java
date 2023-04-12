package com.idanchuang.cms.server.interfaces.adcontentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author xf
 * @Date 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BulletinDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "platformType")
    private Integer platformType;

    @JsonProperty(value = "position")
    private Integer position;

    @JsonProperty(value = "targetLevels")
    private String targetLevels;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "startTime")
    private LocalDateTime startTime;

    @JsonProperty(value = "endTime")
    private LocalDateTime endTime;

    @JsonProperty(value = "isJump")
    private Integer isJump;

    @JsonProperty(value = "jumpType")
    private Integer jumpType;

    @JsonProperty(value = "jumpUrl")
    private String jumpUrl;

    @JsonProperty(value = "textTitle")
    private String textTitle;

    @JsonProperty(value = "textContent")
    private String textContent;
}
