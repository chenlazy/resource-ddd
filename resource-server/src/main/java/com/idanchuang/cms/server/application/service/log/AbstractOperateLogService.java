package com.idanchuang.cms.server.application.service.log;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:18
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public abstract class AbstractOperateLogService {

    /**
     * 构建日志信息
     *
     * @param field
     * @param bizNo
     * @param desc
     * @param logType
     * @param objects
     * @return
     */
    public abstract void writeLog(String field, StringBuilder bizNo, StringBuilder desc, String logType, Object objects);

}
