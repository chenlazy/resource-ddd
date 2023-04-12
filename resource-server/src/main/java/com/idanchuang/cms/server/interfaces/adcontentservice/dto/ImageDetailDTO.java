package com.idanchuang.cms.server.interfaces.adcontentservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: hulk.Wang
 **/
@Data
public class ImageDetailDTO implements Serializable {

    private String fileUrl;

    private Integer width;

    private Integer height;
}
