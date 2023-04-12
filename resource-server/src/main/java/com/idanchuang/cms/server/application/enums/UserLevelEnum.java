package com.idanchuang.cms.server.application.enums;

import com.idanchuang.cms.server.infrastructure.shard.SystemConstant;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 17:20
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public enum UserLevelEnum {

    /**
     * 用户等级
     */
    REGISTER(1, SystemConstant.REGISTER_LEVEL_NAME),
    VIP(2, SystemConstant.VIP_LEVEL_NAME),
    VIP_3(3, SystemConstant.VVIP_LEVEL_NAME),
    SVIP(4, SystemConstant.SVIP_LEVEL_NAME),
    BLACK(5, SystemConstant.BLACK_CARD_LEVEL_NAME),
    ;
    private int id;
    private String name;

    UserLevelEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String getNameById(String ids) {
        int id = Integer.parseInt(ids);
        for (UserLevelEnum userLevelEnum : values()) {
            if (userLevelEnum.getId() == id) {
                return userLevelEnum.getName();
            }
        }
        throw new IllegalArgumentException("UserLevelEnum id " + id + " is illegal");
    }
}
