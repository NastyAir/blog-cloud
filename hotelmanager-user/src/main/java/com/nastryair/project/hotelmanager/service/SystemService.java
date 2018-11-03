package com.nastryair.project.hotelmanager.service;

import com.alibaba.fastjson.JSON;
import com.nastryair.project.hotelmanager.common.PermissionCache;
import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.config.UserCenterConf;
import com.nastryair.project.hotelmanager.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.common.contant.MessageTypeConstant;
import com.nastryair.project.hotelmanager.entity.MessageEntity;
import com.nastryair.project.hotelmanager.entity.Permission;
import com.nastryair.project.hotelmanager.entity.User;
import com.nastryair.project.hotelmanager.respository.MessageRepository;
import com.nastryair.project.hotelmanager.respository.PermissionRepository;
import com.nastryair.project.hotelmanager.respository.RoleRepository;
import com.nastryair.project.hotelmanager.respository.UserRepository;
import com.nastryair.project.hotelmanager.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.nastryair.project.hotelmanager.common.config.UserCenterConf.MODULE_NAME;
import static com.nastryair.project.hotelmanager.common.config.UserCenterConf.jwtSecretKey;
import static com.nastryair.project.hotelmanager.common.config.UserCenterConf.userTokenExpiryDate;
import static com.nastryair.project.hotelmanager.util.JwtUtil.JWT_SEPARATOR;

/**
 * SystemServiceImpl class
 *
 * @author QWL
 * @date 2018/7/11
 */
@Service
public class SystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MODULE_NAME);

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final MessageRepository messageRepository;
    private final EntityManager entityManager;

    @Autowired
    public SystemService(UserRepository userRepository, PermissionRepository permissionRepository, UserService userService, RoleRepository roleRepository, MessageRepository messageRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
        this.entityManager = entityManager;
    }

    /**
     * 登陆接口
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆成败信息
     */
    public RestMessage authentication(String username, String password) {
        RestMessage message = new RestMessage();
        User user = userRepository.findByName(username);
        // 用户名为空
        if (user == null) {
//            createLoginMsg("", "登录失败", "登录用户不存在");
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            LOGGER.info("[" + username + "] 登陆失败,登录用户不存在");
            return message;
        }

        entityManager.detach(user); // 将实体状态由持久状态改为游离状态（解决写日志是要去修改用户）
        // 密码不匹配
        if (!user.getPassword().equals(password)) {
//            createLoginMsg(user.getUserId(), "登录失败", "登录密码错误");
            LOGGER.info("[" + username + "] 登陆失败,登录密码错误");
            message.setCode(CodeConstant.FAIL.getCode());
            createLoginMsg(user.getUserId(), "登录通知", "[" + username + "] 登陆失败,登录密码错误");
            return message;
        }
        //生成 有效期x个月的token
        String token = getToken(user.getUserId(), userTokenExpiryDate, Calendar.DAY_OF_MONTH);

        List<Permission> permissions = permissionRepository.findPermissionsByUserId(user.getUserId());
        List<String> permList = new ArrayList<>();
        for (Permission permission : permissions) {
            permList.add(permission.getCode());
        }
        PermissionCache.putPermissionMap(user.getUserId(), permList);
        //设置返回信息
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        user.setPassword("");
        data.put("user", JSON.toJSONString(user));
//        data.put("permission", permCodeToJsonStr(permList));
        message.setData(data);
        message.setCode(CodeConstant.SUCCESS.getCode());
        LOGGER.info("[" + username + "] 登陆成功");
        //发送登录通知消息
        createLoginMsg(user.getUserId(), "登录通知", "用户登录成功");
        return message;
    }


//    
//    public void authentication(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String redirect = AuthUtil.authRedirect(UserCenterConf.unifiedAuthBaseUrl, UserCenterConf.unifiedAuthClientId, UserCenterConf.unifiedAuthRedirectUri,
//                    UserCenterConf.unifiedAuthResponseType, UserCenterConf.unifiedAuthScope, UserCenterConf.unifiedAuthState);
//            response.sendRedirect(redirect);
//        } catch (Exception e) {
//            LOGGER.error(ExceptionUtils.getMessage(e));
//        }
//    }


//    
//    public RestMessage authLogin(HttpServletRequest request, HttpServletResponse response) {
//        RestMessage message = new RestMessage();
//
//        String authCode = request.getParameter(AuthConstant.UNIFIED_AUTH_CODE);
//        String authState = request.getParameter(AuthConstant.UNIFIED_AUTH_STATE);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("state", authState);
//        params.add("code", authCode);
//        params.add("token_name", "access_token");
//        params.add("grant_type", "authorization_code");
//        params.add("redirect_uri", UserCenterConf.unifiedAuthRedirectUri);
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//
//        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//        RestTemplate restTemplate = restTemplateBuilder.basicAuthorization(UserCenterConf.unifiedAuthClientId, UserCenterConf.unifiedAuthClientSecret).build();
//        ResponseEntity<LinkedHashMap> authResponse = restTemplate.postForEntity(UserCenterConf.unifiedAuthGetTokenUrl, requestEntity, LinkedHashMap.class);
//
//        if (authResponse.getStatusCode() == HttpStatus.OK) {
//            LinkedHashMap body = authResponse.getBody();
//            if (body == null) {
//                message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR_BODY_EMPTY));
//                return message;
//            }
//            String token = (String) body.get(AuthConstant.UNIFIED_AUTH_ACCESS_TOKEN);
//            Claims claims = AuthUtil.parserAuthToken(UserCenterConf.unifiedAuthClientSecret, token);
//            if (claims == null) {
//                message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR_TOKEN_ERROR));
//                return message;
//            }
//            List roleList = (List) claims.get(AuthConstant.UNIFIED_AUTH_RELEASE_VERSION);
//            String username = (String) claims.get(AuthConstant.UNIFIED_AUTH_USER_NAME);
//            return authentication(roleList, username);
//        } else {
//            message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR));
//            return message;
//        }
//    }

    /**
     * 认证接口
     *
     * @param token         认证的token
     * @param interfaceName 访问的接口名称
     * @param param         参数
     * @return 认证通过与否信息
     */


    public RestMessage authorization(String token, String interfaceName, String param) {
        RestMessage message = new RestMessage();
        //解析token
        String tokenUserId;
        try {
            tokenUserId = Jwts.parser()
                    //设置签名秘钥
                    .setSigningKey(jwtSecretKey)
                    //替换 token 的header prefix ，设置token内容
                    .parseClaimsJws(token.replace(JWT_SEPARATOR, ""))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
//            e.printStackTrace();
            LOGGER.info("鉴权失败，token解析失败，token=" + token + " interfaceName=" + interfaceName + " param=" + param);
            message.setCode(CodeConstant.FAIL.getCode());
            message.setMsg("token error");
            return message;
        }

        //查询是否有访问当前接口权限
        long count = permissionRepository.countByUserIdAndCode(tokenUserId, interfaceName);
        if (count > 0) {
            message.setCode(CodeConstant.SUCCESS.getCode());
            LOGGER.info("鉴权成功，token=" + token + " interfaceName=" + interfaceName + " param=" + param);
            message.setData(tokenUserId);
        } else {
            LOGGER.info("鉴权失败，无当前接口权限，token=" + token + " interfaceName=" + interfaceName + " param=" + param);
            message.setCode(CodeConstant.FAIL.getCode());
        }
        return message;
    }

    /**
     * 生成token
     *
     * @param subject     token保存内容
     * @param overdueNum  过期时间 长度
     * @param overdueUnit 过期时间 单位
     */
    private String getToken(String subject, int overdueNum, int overdueUnit) {
        //获取token失效日期
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(overdueUnit, overdueNum);
        date = calendar.getTime();
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }


    //    /**
//     * 登录接口（通过统一平台认证后的登录）
//     */
//    private RestMessage authentication(List roleList, String username) {
//        RestMessage message = new RestMessage();
//        username = username + AuthConstant.AUTH_USER_SIGN; // 添加用户名后缀，防止与直接注册用户重名
//        User user = userRepository.findByName(username);
//        // 用户名为空【创建用户和角色】
//        if (user == null) {
//            // 判断角色是否为空
//            if (roleList == null || roleList.size() == 0) {
//                message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR_ROLE_EMPTY));
//                return message;
//            }
//
//            // 添加用户
//            User userInfo = new User();
//            try {
//                userInfo.setName(username);
//                userInfo.setNickname(username);
//                userInfo.setPassword(DigestUtils.sha1Hex(AuthConstant.DEFAULT_PASSWORD));
//
//                message = userService.create(JSON.toJSONString(userInfo));
//                if (message.getCode() % 1000 != 1) {
//                    message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR_CREATE_USER));
//                    return message;
//                }
//                String userId = (String) message.getData();
//                user = userRepository.findByUserId(userId);
//
//                // 添加权限
//                StringBuilder roleIds = new StringBuilder();
//                for (Object aRoleList : roleList) {
//                    String roleName = (String) aRoleList;
//                    Role role = roleRepository.findByName(roleName);
//                    if (role == null) {
//                        continue;
//                    }
//                    roleIds.append(role.getRoleId()).append(",");
//                }
//                String roleIdStr = roleIds.toString();
//                if (roleIdStr.equals("")) {
//                    userRepository.delete(user);
//                    message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR_ROLE_EMPTY));
//                    return message;
//                }
//
//                if (roleIdStr.lastIndexOf(",") == roleIdStr.length() - 1) {
//                    roleIdStr = roleIdStr.substring(0, roleIdStr.length() - 1);
//                }
//                String userRoleStr = "{\"userId\":\"" + user.getUserId() + "\",\"roleId\":[\"" + roleIdStr + "\"]}";
//                message = userService.createRole(userRoleStr);
//                if (message.getCode() % 1000 != 1) {
//                    userRepository.delete(user);
//                    message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR_CREATE_ROLE));
//                    return message;
//                }
//            } catch (Exception e) {
//                LOGGER.error(ExceptionUtils.getMessage(e));
//                if (user != null) {
//                    userRepository.delete(user);
//                }
//                message.setCode(CodeConstant.getValue(CodeConstant.AUTHORIZE_ERROR_CREATE_USER));
//                return message;
//            }
//        }
//
//        //生成 有效期x个月的token
//        String token = getToken(user.getUserId(), userTokenExpiryDate, Calendar.DAY_OF_MONTH);
//
//        List<Permission> permissions = permissionRepository.findPermissionsByUserId(user.getUserId());
//        List<String> permList = new ArrayList<>();
//        for (Permission permission : permissions) {
//            permList.add(permission.getCode());
//        }
//        PermissionCache.putPermissionMap(user.getUserId(), permList);
//        //设置返回信息
//        Map<String, String> data = new HashMap<>();
//        data.put("token", token);
//        user.setPassword("");
//        data.put("user", JSON.toJSONString(user));
//        message.setData(data);
//        message.setCode(CodeConstant.getValue(CodeConstant.AUTH_SUCCESS));
//        //发送登录通知消息
//        createLoginMsg(user.getUserId(), "登录通知", "用户登录成功");
//        return message;
//    }
//
    private void createLoginMsg(String userId, String title, String content) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(MODULE_NAME);
        messageEntity.setCode((CodeConstant.SUCCESS.getCode()) + "");
        messageEntity.setType(MessageTypeConstant.SYSTEM_MESSAGE);
        messageEntity.setTitle(title);
        messageEntity.setBusinessType(MessageTypeConstant.getBusinessTypeByType(MessageTypeConstant.SYSTEM_MESSAGE));
        messageEntity.setContent(content);
        messageEntity.setRecipient(userId);
        messageEntity.setMessageId(UUID.randomUUID().toString());
        messageEntity.setStatus("NEW");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageEntity.setCreateTime(sdf.format(new Date()));
        messageRepository.save(messageEntity);
    }
}
