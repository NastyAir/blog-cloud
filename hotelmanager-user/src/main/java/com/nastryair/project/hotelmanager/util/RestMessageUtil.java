package com.nastryair.project.hotelmanager.util;

import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.contant.CodeConstant;

public class RestMessageUtil {
    public static RestMessage success(Object object) {
        RestMessage restMessage = new RestMessage();
        restMessage.setCode(CodeConstant.SUCCESS.getCode());
        restMessage.setMsg("操作成功");
        restMessage.setData(object);
        return restMessage;
    }

    public static RestMessage success() {
        return success(null);
    }
    public static RestMessage error() {
        return error(CodeConstant.FAIL.getCode(),"未知错误");
    }

    public static RestMessage error(Integer code, String msg) {
        RestMessage result = new RestMessage();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
