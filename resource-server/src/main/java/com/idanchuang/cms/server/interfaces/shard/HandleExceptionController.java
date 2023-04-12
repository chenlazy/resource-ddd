package com.idanchuang.cms.server.interfaces.shard;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.api.common.enums.ErrorEnum;
import com.idanchuang.cms.server.infrastructure.shard.InfraException;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.exception.common.ErrorCode;
import com.idanchuang.component.base.exception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-10-21 10:30
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */

@Slf4j
@RestControllerAdvice
public class HandleExceptionController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ExceptionHandler(value = Exception.class)
    public JsonResult<?> handle(Exception exp) {

        JsonResult<Void> warnRes = null;
        if (exp instanceof HttpRequestMethodNotSupportedException) {
            warnRes = JsonResult.failure(ErrorCode.METHOD_NOT_ALLOWED);
        } else if (exp instanceof HttpMessageNotReadableException) {
            warnRes = JsonResult.failure(ErrorEnum.BAD_REQUEST);
        } else if (exp instanceof BindException) {
            BindingResult bindingResult = ((BindException) exp).getBindingResult();
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldErrors().get(0);
                warnRes = JsonResult.failure(ErrorEnum.BAD_REQUEST, fieldError.getDefaultMessage());
            }
        } else if (exp instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) exp).getBindingResult();
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldErrors().get(0);
                warnRes = JsonResult.failure(fieldError.getDefaultMessage());
            }
        } else if (exp instanceof IllegalArgumentException) {
            warnRes = JsonResult.failure(exp.getMessage());
        } else if (exp instanceof com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException) {
            com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException serviceException = findThisServiceException(exp);
            if (serviceException != null) {
                warnRes = JsonResult.failure(((com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException) exp).getUserMessage());
            }
        } else if (exp instanceof InfraException) {
            if (exp != null) {
                warnRes = JsonResult.failure((exp.getMessage()));
            }
        } else {
            BusinessException serviceException = findServiceException(exp);
            if (serviceException != null) {
                warnRes = JsonResult.failure(((BusinessException) exp).getExDefinition());
            }
        }

        if (warnRes != null) {
            log.warn("controller info -- url:[ {} ] param:[ {} ] warnRes:{} exp:{}",
                    httpServletRequest.getRequestURI(), JSON.toJSONString(httpServletRequest.getParameterMap()), warnRes, exp.getMessage());
            return warnRes;
        }
        log.error("controller error -- url:[ {} ] param:[ {} ]  exp:{}",
                httpServletRequest.getRequestURI(), JSON.toJSONString(httpServletRequest.getParameterMap()), exp.getMessage(), exp);

        return JsonResult.failure(ErrorEnum.UNKNOWN_ERROR);
    }

    private BusinessException findServiceException(Throwable e) {
        return e instanceof BusinessException ? (BusinessException) e : null;
    }

    private com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException findThisServiceException(Throwable e) {
        return e instanceof com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException ? (com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException) e : null;
    }
}
