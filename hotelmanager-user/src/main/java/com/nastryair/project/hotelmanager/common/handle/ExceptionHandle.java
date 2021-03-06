package com.nastryair.project.hotelmanager.common.handle;

import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.exception.BusinessException;
import com.nastryair.project.hotelmanager.util.RestMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 廖师兄
 * 2017-01-21 13:59
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestMessage handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException girlException = (BusinessException) e;
            return RestMessageUtil.error(girlException.getCode(), girlException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return RestMessageUtil.error();
        }
    }
}
