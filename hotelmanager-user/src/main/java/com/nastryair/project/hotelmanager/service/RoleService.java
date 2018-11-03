package com.nastryair.project.hotelmanager.service;

import com.alibaba.fastjson.JSON;
import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.common.contant.MessageTypeConstant;
import com.nastryair.project.hotelmanager.entity.*;
import com.nastryair.project.hotelmanager.respository.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.nastryair.project.hotelmanager.common.config.UserCenterConf.MODULE_NAME;

/**
 * @author QWL
 * @date 2018/4/12
 */
@Service
public class RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MODULE_NAME);
    private final RoleRepository roleRepository;

    private final RolePermissionRepository rolePermissionRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository, UserRepository userRepository, MessageRepository messageRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.permissionRepository = permissionRepository;
    }


    public RestMessage info(String roleId) {
        RestMessage message = new RestMessage();
        Role role = roleRepository.findByRoleId(roleId);
        if (role != null) {
            message.setData(role);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }


    public RestMessage list(String name, String order, String orderColumn, String currentPage, String pageSize) {
        RestMessage message = new RestMessage();

        Sort.Direction direction = "ASC".equals(order.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(
                Integer.valueOf(currentPage),
                Integer.valueOf(pageSize),
                Sort.by(direction, orderColumn));
        Page<Role> page;
        //判断名称是否为空
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        try {
            page = roleRepository.findAll(specification, pageable);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }

        message.setCode(CodeConstant.SUCCESS.getCode());
        message.setData(page);
        return message;
    }


    public RestMessage create(String roleJson) {
        RestMessage message = new RestMessage();
        Role role = JSON.parseObject(roleJson, Role.class);
        Role sameRole = roleRepository.findByName(role.getName());
        if (sameRole != null) {
            message.setCode(CodeConstant.RECORD_ALREADY_EXISTS.getCode());
            return message;
        }
        role.setRoleId(UUID.randomUUID().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        role.setCreateTime(simpleDateFormat.format(new Date()));
        try {
            message.setCode(CodeConstant.SUCCESS.getCode());
            message.setData(roleRepository.save(role).getRoleId());
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        return message;
    }


    @Transactional
    public RestMessage alter(String roleJson) {
        RestMessage message = new RestMessage();
        Role newRole = JSON.parseObject(roleJson, Role.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRole.setUpdateTime(simpleDateFormat.format(new Date()));
        Role role = roleRepository.findByRoleId(newRole.getRoleId());
        if (role == null) {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            return message;
        }
        try {
            roleRepository.updateByRoleId(
                    newRole.getName(),
                    newRole.getDescription(),
                    newRole.getUpdateTime(),
                    newRole.getRoleId()
            );

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }


    @Transactional
    public RestMessage drop(String roleId) {
        RestMessage message = new RestMessage();
        Role role = roleRepository.findByRoleId(roleId);
        if (role == null) {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
            return message;
        }
        List<Permission> permissionList = permissionRepository.findPermByRoleId(roleId);
        if (permissionList.size() > 0) {
            message.setCode(CodeConstant.FAIL.getCode());
            message.setMsg("当前角色存在关联的权限，请先删除权限关联关系");
            return message;
        }
        try {
            roleRepository.deleteByRoleId(roleId);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }


    @Transactional
    public RestMessage addRolePerms(String roleId, List permissionIdList) {
        RestMessage message = new RestMessage();
        List<String> permissionNames = new ArrayList<>();
        //删除关联
        rolePermissionRepository.deleteByRoleId(roleId);
        //新增关联
        List<RolePermission> newRolePermissionList = new ArrayList<>();
        for (Object permIdObj : permissionIdList) {
            String permId = (String) permIdObj;
            newRolePermissionList.add(new RolePermission(roleId, permId));
            Permission permission = permissionRepository.findByPermissionId(permId);
            permissionNames.add(permission.getName());
        }
        rolePermissionRepository.saveAll(newRolePermissionList);
        //发送权限变更消息
        List<User> userList = userRepository.findByRoleId(roleId);
        Role role = roleRepository.findByRoleId(roleId);
        String roleName = role.getName();
        for (User user : userList) {
            createPermissionChangeMsg(user.getUserId(), "权限变动通知", "您的角色【" + roleName + "】所关联的权限已被管理员修改，现在角色【" + roleName + "】关联的权限如下：" + permissionNames);
        }
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }

    private void createPermissionChangeMsg(String recipient, String title, String content) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(MODULE_NAME);
        messageEntity.setCode(CodeConstant.SUCCESS.getCode() + "");
        messageEntity.setType(MessageTypeConstant.SYSTEM_MESSAGE);
        messageEntity.setTitle(title);
        messageEntity.setBusinessType(MessageTypeConstant.getBusinessTypeByType(MessageTypeConstant.SYSTEM_MESSAGE));
        messageEntity.setContent(content);
        messageEntity.setRecipient(recipient);
        messageEntity.setMessageId(UUID.randomUUID().toString());
        messageEntity.setStatus("NEW");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageEntity.setCreateTime(sdf.format(new Date()));
        messageRepository.save(messageEntity);
    }


    public RestMessage nameList() {
        RestMessage message = new RestMessage();
        List<Role> roleList;
        try {
            roleList = roleRepository.findNameList();
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        message.setData(roleList);
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }


    public RestMessage getByUserId(String userId) {
        RestMessage message = new RestMessage();
        List<Role> roleList = roleRepository.findByUserId(userId);
        if (roleList != null) {
            message.setData(JSON.toJSONString(roleList));
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }


    public RestMessage getByRoleName(String name) {
        RestMessage message = new RestMessage();
        Role roleList = roleRepository.findByName(name);
        if (roleList != null) {
            message.setData(JSON.toJSONString(roleList));
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;
    }
}
