package com.idanchuang.resource.server.infrastructure.common.constant;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 15:53
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public enum BrokenLineShowEnum {

    ONE_MONTH(0, 30, 1),
    TWO_MONTH(30, 60, 2),
    THREE_MONTH(60, 90, 7),
    MAX_MONTH(90, 10000, 15);

    private Integer start;

    private Integer end;

    private Integer showNum;


    BrokenLineShowEnum(Integer start, Integer end, Integer showNum) {
        this.start = start;
        this.end = end;
        this.showNum = showNum;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public Integer getShowNum() {
        return showNum;
    }

    public static Integer getShowNum(Integer dayNum) {
        if (null == dayNum || dayNum < 0) {
            return null;
        }
        for (BrokenLineShowEnum obe : values()) {
            if ((obe.getStart() < dayNum) && (dayNum <= obe.getEnd())) {
                return obe.getShowNum();
            }
        }
        return null;
    }
}
