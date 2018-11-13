package com.nastryair.project.hotelmanager.hotel.util;

import com.nastryair.project.hotelmanager.hotel.common.RestMessage;
import com.nastryair.project.hotelmanager.hotel.common.contant.CodeConstant;
import org.springframework.http.ResponseEntity;

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
        return error(CodeConstant.FAIL.getCode(), "未知错误");
    }

    public static RestMessage error(Integer code, String msg) {
        RestMessage result = new RestMessage();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static RestMessage from(ResponseEntity<RestMessage> responseMessage) {
        RestMessage msg = new RestMessage();
        msg.setCode(responseMessage.getBody().getCode());
        if (responseMessage.getBody().getMsg() != null) {
            msg.setMsg(responseMessage.getBody().getMsg());
        }

        if (responseMessage.getBody().getData() != null) {
            msg.setData(responseMessage.getBody().getData());
        }

        return msg;
    }
}
