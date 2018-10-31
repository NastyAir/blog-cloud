package com.nastryair.project.hotelmanager.service;

import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.common.exception.BusinessException;
import com.nastryair.project.hotelmanager.entity.User;
import com.nastryair.project.hotelmanager.respository.UserRepository;
import com.nastryair.project.hotelmanager.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;

    @Autowired
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RestMessage login(String userName, String password, boolean rememberMe) {
        RestMessage restMessage = new RestMessage();
        User user = userRepository.findByName(userName);
        if (user == null) {
            throw new BusinessException(CodeConstant.TARGET_NOT_FIND.getCode(), "用户不存在");
        }
        if (StringUtils.equals(user.getPassword(), password)) {
            JwtUtil.buildJWT(user.getUserId());
            restMessage.setCode(CodeConstant.SUCCESS.getCode());
            restMessage.setMsg("用户登陆成功");

        }else {
            restMessage.setCode(CodeConstant.FAIL.getCode());
            restMessage.setMsg("密码错误");
        }
        return restMessage;
    }
}
