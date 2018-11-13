package com.nastryair.project.hotelmanager.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.common.contant.MessageTypeConstant;
import com.nastryair.project.hotelmanager.dto.UserPasswordDto;
import com.nastryair.project.hotelmanager.dto.UserRoleDto;
import com.nastryair.project.hotelmanager.entity.*;
import com.nastryair.project.hotelmanager.repository.*;
import com.nastryair.project.hotelmanager.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.nastryair.project.hotelmanager.common.config.UserCenterConf.MODULE_NAME;


@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MODULE_NAME);

    private final UserRepository userRepository;
    private final UserAvatarRepository userAvatarRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserAvatarRepository userAvatarRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.userAvatarRepository = userAvatarRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
    }


    public RestMessage create(User userInfo) {
        RestMessage message = new RestMessage();
        // 保存用户，判断用户名是否存在
        try {
            User user = userRepository.findByName(userInfo.getName());
            if (user == null) {
                userInfo.setUserId(UUID.randomUUID().toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userInfo.setCreateDate(new Date());
                message.setData(userRepository.save(userInfo).getUserId());
                message.setCode(CodeConstant.SUCCESS.getCode());
            } else {
                message.setCode(CodeConstant.RECORD_ALREADY_EXISTS.getCode());
            }
        } catch (Exception e) {
            LOGGER.error(ExceptionUtil.getStackInfo(e));
            message.setCode(CodeConstant.FAIL.getCode());
        }
        return message;
    }


    public RestMessage drop(String userId) {
        RestMessage message = new RestMessage();
        User userInfo = userRepository.findByUserId(userId);
        if (userInfo == null) {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            return message;
        }
        List<Role> roleList = roleRepository.findByUserId(userId);
        if (roleList.size() > 0) {
            message.setCode(CodeConstant.FAIL.getCode());
            message.setMsg("当前用户存在关联的角色，请先删除角色关联关系");
            return message;
        }
        try {
            userRepository.delete(userInfo);
            message.setCode(CodeConstant.SUCCESS.getCode());

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
        }
        return message;
    }


    public RestMessage alter(String userStr) {
        RestMessage message = new RestMessage();

        User newUser = JSON.parseObject(userStr, User.class);
        User oldUser = userRepository.findByUserId(newUser.getUserId());
        if (oldUser == null) {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            return message;
        }
        if (!StringUtils.equals(oldUser.getName(), newUser.getName())) {
            User user = userRepository.findByName(newUser.getName());
            if (user != null) {
                message.setCode(CodeConstant.RECORD_ALREADY_EXISTS.getCode());
                return message;
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        newUser.setId(oldUser.getId());
        newUser.setUserId(oldUser.getUserId());
        newUser.setName(newUser.getName() != null ? newUser.getName() : oldUser.getName());
        newUser.setNickname(newUser.getNickname() != null ? newUser.getNickname() : oldUser.getNickname());
//        newUser.setPassword(newUser.getPassword() != null ? newUser.getPassword() : oldUser.getPassword());
        newUser.setPhoneNumber(newUser.getPhoneNumber() != null ? newUser.getPhoneNumber() : oldUser.getPhoneNumber());
        newUser.setBirthday(newUser.getBirthday() != null ? newUser.getBirthday() : oldUser.getBirthday());
//        newUser.setProvince(newUser.getProvince() != null ? newUser.getProvince() : oldUser.getProvince());
//        newUser.setCity(newUser.getCity() != null ? newUser.getCity() : oldUser.getCity());
//        newUser.setDistrict(newUser.getDistrict() != null ? newUser.getDistrict() : oldUser.getDistrict());
        newUser.setAvatar(newUser.getAvatar() != null ? newUser.getAvatar() : oldUser.getAvatar());
        newUser.setState(newUser.getState() != null ? newUser.getState() : oldUser.getState());

//        newUser.setDescription(newUser.getDescription() != null ? newUser.getDescription() : oldUser.getDescription());
//        newUser.setUpdateTime(dateFormat.format(new Date()));
//        newUser.setCreateTime(oldUser.getCreateTime());
        if (newUser.getPassword() == null && oldUser.getPassword() != null) {
            newUser.setPassword(oldUser.getPassword());
        }
        if (newUser.getDescription() == null && oldUser.getDescription() != null) {
            newUser.setDescription(oldUser.getDescription());
        }
        try {
            userRepository.save(newUser);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            message.setCode(CodeConstant.FAIL.getCode());
            LOGGER.error(ExceptionUtils.getMessage(e));
        }
        return message;
    }


    @Transactional
    public RestMessage alterPassword(String userStr) {
        RestMessage message = new RestMessage();
        //获取用户
        UserPasswordDto newUser = JSON.parseObject(userStr, UserPasswordDto.class);
        User oldUser = userRepository.findByUserId(newUser.getUserId());
        if (oldUser == null) {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            return message;
        }
        //对比密码
        if (!StringUtils.equals(oldUser.getPassword(), newUser.getPassword())) {
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        //更新密码
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            userRepository.updatePassword(
                    newUser.getNewPassword(),
                    dateFormat.format(new Date()),
                    newUser.getUserId()
            );
            message.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            message.setCode(CodeConstant.FAIL.getCode());
            LOGGER.error(ExceptionUtils.getMessage(e));
        }

        return message;
    }


    public RestMessage list(String userName, String order, String orderColumn, String currentPage, String pageSize) {
        RestMessage message = new RestMessage();

        Sort.Direction direction = "ASC".equals(order.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(
                Integer.valueOf(currentPage),
                Integer.valueOf(pageSize),
                Sort.by(direction, orderColumn));
        Page page;
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(userName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + userName + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        try {
            page = userRepository.findAll(specification, pageable);
            message.setData(page);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            message.setCode(CodeConstant.FAIL.getCode());

            LOGGER.error(ExceptionUtils.getMessage(e));
        }

        return message;
    }


    public RestMessage userInfo(String userId) {
        RestMessage message = new RestMessage();

        User userInfo = userRepository.findByUserId(userId);
        if (userInfo != null) {
            message.setData(userInfo);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }


    public RestMessage findUserByName(String userName) {
        RestMessage message = new RestMessage();

        User userInfo = userRepository.findByName(userName);
        if (userInfo != null) {
            message.setData(userInfo);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }


    public RestMessage roleInfo(String userId) {
        RestMessage message = new RestMessage();
        List<UserRole> userRoleList = userRoleRepository.findByUserId(userId);
        if (userRoleList != null) {
            try {
                ArrayList<UserRoleDto> userRoles = new ArrayList<>();
                for (UserRole userRole : userRoleList) {
                    Role role = roleRepository.findByRoleId(userRole.getRoleId());
                    if (role != null) {

                        UserRoleDto userRoleDto = new UserRoleDto();
                        userRoleDto.setId(role.getId());
                        userRoleDto.setUserId(userRole.getUserId());
                        userRoleDto.setRoleId(userRole.getRoleId());
                        userRoleDto.setName(role.getName());
                        userRoleDto.setUpdateTime(role.getUpdateTime());
                        userRoleDto.setCreateTime(role.getCreateTime());
                        userRoleDto.setDescription(role.getDescription());
                        userRoles.add(userRoleDto);
                    }
                }
                message.setData(userRoles);
                message.setCode(CodeConstant.SUCCESS.getCode());
            } catch (Exception e) {
                message.setCode(CodeConstant.FAIL.getCode());
            }
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }


    @Transactional
    public RestMessage createRole(String userRoleStr) {
        RestMessage message = new RestMessage();
        List<String> roleNames = new ArrayList<>();
        try {
            JSONObject jsonObject = JSON.parseObject(userRoleStr);
            String userId = jsonObject.getString("userId");
            JSONArray roleIdArray = jsonObject.getJSONArray("roleId");
            userRoleRepository.deleteByUserId(userId);
            for (Object roleId : roleIdArray) {
                userRoleRepository.save(new UserRole(userId, (String) roleId));
                Role role = roleRepository.findByRoleId((String) roleId);
                roleNames.add(role.getName());
            }
            //发送角色变更通知
            createRoleChangeMsg(userId, "权限变动通知", "您的用户角色已被管理员修改，现在的角色如下:" + roleNames);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
        }
        return message;
    }

    private void createRoleChangeMsg(String userId, String title, String content) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(MODULE_NAME);
        messageEntity.setCode(CodeConstant.SUCCESS.getCode() + "");
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


    public RestMessage getByRoleId(String roleId) {
        RestMessage message = new RestMessage();
        List<User> userList = userRepository.findByRoleId(roleId);
        if (userList != null) {
            message.setData(userList);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }


    public RestMessage getByRoleName(String name) {
        RestMessage message = new RestMessage();
        List<User> userList = userRepository.getByRoleName(name);
        if (userList != null) {
            message.setData(userList);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }


    @Transactional
    public RestMessage alterAvatar(MultipartFile multipartFile, String userId) {
        RestMessage message = new RestMessage();
        if (userAvatarRepository.countByUserId(userId) > 0) {
            userAvatarRepository.deleteByUserId(userId);
        }
        byte[] avatarBytes;
        String avatarFormat;
        try {
            avatarBytes = multipartFile.getBytes();
            String fileName = multipartFile.getOriginalFilename();
            avatarFormat = fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (IOException e) {
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        UserAvatar userAvatar = new UserAvatar();
        userAvatar.setUserId(userId);
        userAvatar.setAvatar(avatarBytes);
        userAvatar.setAvatarFormat(avatarFormat);
        try {
            userAvatarRepository.save(userAvatar);
//            userAvatarRepository.updateAvatar(avatarBytes, avatarFormat, userId);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } catch (Exception e) {
            LOGGER.error(ExceptionUtil.getStackInfo(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        return message;
    }


    public void getAvatar(String userId, HttpServletResponse response) {
        UserAvatar userAvatar = userAvatarRepository.findByUserId(userId);
        byte[] data;
        String fileName;
        if (userAvatar == null) {
            fileName = "default_avatar.png";
            try {
                //读取jar包内的默认头像图片
                ClassPathResource resource = new ClassPathResource("default_avatar.png");
                InputStream inputStream = resource.getInputStream();
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100];
                int rc = 0;
                while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                data = swapStream.toByteArray();
            } catch (IOException e) {
                LOGGER.error(ExceptionUtil.getStackInfo(e));
                return;
            }
        } else {
            data = userAvatarRepository.findAvatarByUserId(userId);
            fileName = "avatar." + userAvatar.getAvatarFormat();
        }
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(ExceptionUtil.getStackInfo(e));
        }
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            LOGGER.error(ExceptionUtil.getStackInfo(e));
        }
    }


//    public RestMessage findByAccessId(String accessId) {
//        RestMessage message = new RestMessage();
//        User userInfo = userRepository.findByAccessId(accessId);
//        if (userInfo != null) {
//            message.setData(userInfo);
//            message.setCode(CodeConstant.SUCCESS.getCode());
//        } else {
//            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
//        }
//        return message;
//    }


    /**
     * 将权限转list化为json
     */
    private static String permCodeToJsonStr(List<String> permList) {
        JSONObject root = new JSONObject();
        for (String perm : permList) {
            String[] tempStr = perm.split(":");
            String jsonPath = tempStr[0];
            String jsonOption = tempStr[1];

            String[] paths = jsonPath.split("\\.");
            List<String> pathList = new ArrayList<>(Arrays.asList(paths));
            pathList.add(jsonOption);
            findByPath(root, pathList, 0);
        }
        return JSON.toJSONString(root);
    }

    /**
     * 权限处理限递归方法
     */
    private static JSONObject findByPath(JSONObject parent, List<String> paths, int i) {
        String tempPath = paths.get(i);
        JSONObject temp = new JSONObject();
        if (parent.get(tempPath) != null) {
            temp = (JSONObject) parent.get(tempPath);
            parent.put(tempPath, findByPath(temp, paths, i + 1));
        } else {
            if (i < paths.size() - 1) {
                parent.put(tempPath, findByPath(temp, paths, i + 1));
            } else {
                parent.put(tempPath, true);
            }
        }

        return parent;
    }

//    public static void main(String[] args) {
////
////        JSONObject root = new JSONObject();
////        List<String> paths = new ArrayList<>();
////        paths.add("ds");
////        paths.add("datasource");
////        paths.add("meta");
////        paths.add("view");
////        System.out.println(findByPath(root, paths, 0));
//
//        List<String> paths = new ArrayList<>();
//        paths.add("ds.datasource.meta:view");
//        paths.add("ds.datasource.meta:edit");
//        paths.add("user.perm.meta:view");
//        paths.add("user.perm.meta:edit");
//        System.out.println(
//                permCodeToJsonStr(paths)
//        );
//    }
}
