package com.nastryair.project.hotelmanager.hotel.service;

import com.nastryair.project.hotelmanager.hotel.common.RestMessage;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

//    private final UserRepository userRepository;

//    @Autowired
//    public AccountService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public RestMessage login(String userName, String password, boolean rememberMe) {
        RestMessage message = new RestMessage();
//        User user = userRepository.findByName(userName);
//        if (user == null) {
//            throw new BusinessException(CodeConstant.RECORD_NOT_FOUND.getCode(), "用户不存在");
//        }
//        if (StringUtils.equals(user.getPassword(), password)) {
//            String token = JwtUtil.buildJWT(user.getUserId());
//            User returnUser = new User();
//            BeanUtils.copyProperties(user, returnUser);
//            returnUser.setPassword(null);
//            Map<String, Object> dataMap = new HashMap<>();
//            dataMap.put("token", token);
//            dataMap.put("user", returnUser);
//            message.setCode(CodeConstant.SUCCESS.getCode());
//            message.setMsg("用户登陆成功");
//            message.setData(dataMap);
//        } else {
//            message.setCode(CodeConstant.FAIL.getCode());
//            message.setMsg("密码错误");
//        }
        return message;
    }
}
