package com.idanchuang.cms.server.domain.model.cms;

import lombok.Data;

import java.io.Serializable;


@Data
public class PageDegrade implements Serializable {
    private static final long serialVersionUID = 6281953268861171323L;

    private int enabled = 0;
    private String platform;
    private String appVersion;
}
