package com.idanchuang.cms.server.infrastructure.aop;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.idanchuang.bizlog.feign.dto.oplog.OpLogRequestDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.remote.RemoteBizLogService;
import com.idanchuang.cms.server.application.service.log.AbstractOperateLogService;
import com.idanchuang.cms.server.infrastructure.annotation.OperateLog;
import com.idanchuang.cms.server.infrastructure.shard.OperateLogConstant;
import com.idanchuang.cms.server.infrastructure.util.SpringApplicationContext;
import com.idanchuang.cms.server.interfaces.web.config.GatewayUserDTO;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-15 11:39
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Component
@Aspect
@Slf4j
public class OperateLogAspect {

    private final SpringApplicationContext applicationContext;

    private final RemoteBizLogService remoteBizLogService;

    private final ExecutorService operateLogExecutorService;

    @Autowired
    public OperateLogAspect(SpringApplicationContext applicationContext, RemoteBizLogService remoteBizLogService, ExecutorService operateLogExecutorService) {
        this.applicationContext = applicationContext;
        this.remoteBizLogService = remoteBizLogService;
        this.operateLogExecutorService = operateLogExecutorService;
    }

    @Pointcut("@annotation(com.idanchuang.cms.server.infrastructure.annotation.OperateLog)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //入参拼接在url上 例如 /xxx/sss/id=123&ids=1231
        Map<String, String[]> parameterMap = request.getParameterMap();
        //请求方式
        String methodType = request.getMethod();
        //入参拼接在url上 例如 /xxx/sss/{id}
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 拦截方法
        Method method = signature.getMethod();
        OperateLog operateLogMethod = method.getAnnotation(OperateLog.class);
        Class<?> businessClass = operateLogMethod.businessType();
        String url = request.getRequestURL().toString();
        String field = operateLogMethod.field();
        StringBuilder desc = new StringBuilder();
        desc.append(operateLogMethod.desc());
        String module = operateLogMethod.module();
        String logType = operateLogMethod.logType();
        try {
            this.checkParam(url);
        } catch (BusinessException e) {
            return JsonResult.failure(e.getMessage());
        }
        JsonResult methodResult = this.getJsonResult(joinPoint);
        if (methodResult != null && methodResult.isSuccess()) {
            GatewayUserDTO gatewayUserDTO = RequestContext.get();
            //日志处理 异步处理
            operateLogExecutorService.execute(() -> {
                try {
                    StringBuilder bizNo = new StringBuilder();
                    JSONObject param = null;
                    Map paramMap = Maps.newHashMap();
                    // POST PUT 数组json格式入参不做考虑 日志接入需要web接口做修改 修改成使用包装类入参
                    boolean b = OperateLogConstant.POST.equals(methodType) || OperateLogConstant.PUT.equals(methodType);
                    if (OperateLogConstant.OPERATE_LOG_DATA.equals(field)) {
                        this.getFieldByResult(field, methodResult, bizNo);
                    } else {
                        if (b) {
                            param = this.getParam(joinPoint);
                            this.getField(field, param, bizNo);
                        } else {
                            paramMap = this.getParam(pathVariables, parameterMap);
                            this.getField(field, paramMap, bizNo);
                        }
                    }
                    String requestMsg = JSONObject.toJSONString(paramMap);
                    if (businessClass != Void.class) {
                        AbstractOperateLogService bean = (AbstractOperateLogService) applicationContext.getBean(businessClass);
                        if (bean != null) {
                            if (b) {
                                bean.writeLog(field, bizNo, desc, logType, param);
                            } else {
                                bean.writeLog(field, bizNo, desc, logType, paramMap);
                            }
                        }
                    } else {
                        desc.append(bizNo);
                    }
                    //记录操作日志
                    OpLogRequestDTO logContent = this.getLogContent(module, desc, url, bizNo, logType, gatewayUserDTO);
                    remoteBizLogService.writeLog(logContent);
                    //记录操作入参日志
                    if (!OperateLogConstant.OPERATE_LOG_DATA.equals(field)) {
                        OpLogRequestDTO logContentRequest;
                        if (b) {
                            logContentRequest = this.getLogContent(module, desc.append("入参:").append(param.toJSONString()), url, bizNo, logType + OperateLogConstant.REQUEST, gatewayUserDTO);
                        } else {
                            logContentRequest = this.getLogContent(module, desc.append("入参:").append(requestMsg), url, bizNo, logType + OperateLogConstant.REQUEST, gatewayUserDTO);
                        }
                        remoteBizLogService.writeLog(logContentRequest);
                    }
                } catch (Exception e) {
                    log.warn("writeLog warn e:{}", e);
                }
            });
        }
        return methodResult;
    }

    /**
     * 参数校验
     *
     * @param requestUrl
     */
    private void checkParam(String requestUrl) {
        if (StringUtils.isBlank(requestUrl)) {
            log.error("操作日志请求url为空");
            throw new BusinessException("操作日志请求url为空");
        }
    }

    /**
     * 获取入参拼装扩展属性 post put
     *
     * @param field
     * @param param
     * @return
     */
    private void getField(String field, JSONObject param, StringBuilder bizNo) {
        if (param != null && param.containsKey(field)) {
            bizNo.append(param.get(field).toString());
        }
    }

    /**
     * 获取入参拼装扩展属性 get delete
     *
     * @param field
     * @param map
     * @return
     */
    private void getField(String field, Map map, StringBuilder bizNo) {
        if (map != null && map.containsKey(field)) {
            if (map.get(field).getClass() == String[].class) {
                String[] s = (String[]) map.get(field);
                bizNo.append(s[0]);
            } else {
                bizNo.append(map.get(field).toString());
            }
        }
    }

    /**
     * 根据返回结果拼装扩展属性
     *
     * @param field
     * @param methodResult
     * @return
     */
    private void getFieldByResult(String field, JsonResult methodResult, StringBuilder bizNo) {
        if (methodResult.isSuccess()) {
            String reqParam = JSONObject.toJSONString(methodResult);
            JSONObject parse = (JSONObject) JSONObject.parse(reqParam);
            if (parse.containsKey(field)) {
                bizNo.append(parse.get(field).toString());
            }
        }
    }

    /**
     * 日志入参拼装
     *
     * @param module
     * @param desc
     * @param url
     * @param field
     * @param logType
     * @param gatewayUserDTO
     * @return
     */
    private OpLogRequestDTO getLogContent(String module, StringBuilder desc, String url,
                                          StringBuilder field, String logType, GatewayUserDTO gatewayUserDTO) {
        OpLogRequestDTO logInfo = new OpLogRequestDTO();
        logInfo.setUrl(url);
        logInfo.setModule(module);
        logInfo.setLogType(logType);
        logInfo.setLogContent(desc.toString());
        logInfo.setOperateTime(new Date());
        logInfo.setCustomParameters(field.toString());
        logInfo.setIp(gatewayUserDTO.getLastLoginIp());
        logInfo.setOperator(gatewayUserDTO.getRealName());
        logInfo.setOperatorRole(gatewayUserDTO.getRole());
        logInfo.setSysName(SystemConfig.getInstance().getApplicationName());
        log.info("getLogContent logInfo:{}", logInfo);
        return logInfo;
    }

    /**
     * 接口入参 post put 请求
     *
     * @param joinPoint
     * @return
     */
    private JSONObject getParam(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String reqParam = JSONObject.toJSONString(args);
        String substring = reqParam.substring(1, reqParam.length() - 1);
        return (JSONObject) JSONObject.parse(substring);
    }

    /**
     * 接口入参 get delete 请求
     *
     * @param pathVariables
     * @param parameterMap
     * @return
     */
    private Map getParam(Map pathVariables, Map<String, String[]> parameterMap) {
        if (MapUtils.isEmpty(pathVariables)) {
            return parameterMap;
        } else {
            return pathVariables;
        }
    }


    /**
     * 返回结果
     *
     * @param joinPoint
     * @return
     */
    private JsonResult getJsonResult(ProceedingJoinPoint joinPoint) throws Throwable {

        return (JsonResult) joinPoint.proceed();
    }
}
