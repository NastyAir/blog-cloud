package com.nastryair.project.hotelmanager.controller.impl;

import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.controller.SystemApi;
import com.nastryair.project.hotelmanager.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * SystemController class
 *
 * @author QWL
 * @date 2018/7/11
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/system")
public class SystemController implements SystemApi {
    final private SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 登陆接口
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆成败信息
     */
    @Override
    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    public ResponseEntity authentication(@RequestParam String username, @RequestParam String password) {
        RestMessage message = systemService.authentication(username, password);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
//
//    /**
//     * 第三方登录认证
//     */
//    @Override
//    @RequestMapping(value = "/auth/authorize", method = RequestMethod.GET)
//    public void authentication(HttpServletRequest request, HttpServletResponse response) {
//        systemService.authentication(request, response);
//    }
//
//    /**
//     * 第三方登录
//     */
//    @Override
//    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
//    public void authLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        RestMessage message = systemService.authLogin(request, response);
//        if (message.getCode() != CodeConstant.getValue(CodeConstant.AUTH_SUCCESS)) {
//            response.sendRedirect(UserCenterConf.blankPageUrl+"?token=&user=");
//        }
//        //设置返回信息
//        Map<String, String> data = (Map) message.getData();
//        String token = data.get("token");
//        String user = data.get("user");
//        response.sendRedirect(UserCenterConf.blankPageUrl+"?token=" + token + "&user=" + user);
//    }

    /**
     * 认证接口
     *
     * @param token         认证的token
     * @param interfaceName 访问的接口名称
     * @param param         参数
     * @return 认证通过与否信息
     */
    @Override
    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public ResponseEntity authorization(
            @RequestParam String token,
            @RequestParam("interfacename") String interfaceName,
            @RequestParam(required = false) String param
    ) {
        try {
            interfaceName = URLDecoder.decode(interfaceName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RestMessage message = systemService.authorization(token, interfaceName, param);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
