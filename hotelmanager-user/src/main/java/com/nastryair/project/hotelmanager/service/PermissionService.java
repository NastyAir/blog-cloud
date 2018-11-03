package com.nastryair.project.hotelmanager.service;

import com.alibaba.fastjson.JSON;
import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.entity.Permission;
import com.nastryair.project.hotelmanager.respository.PermissionRepository;
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

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static com.nastryair.project.hotelmanager.common.config.UserCenterConf.MODULE_NAME;


/**
 * @author QWL
 * @date 2018/4/12
 */
@Service
public class PermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MODULE_NAME);

    final private PermissionRepository repository;

    @Autowired
    public PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }


    public RestMessage info(String permissionId) {
        RestMessage message = new RestMessage();
        Permission permission = repository.findByPermissionId(permissionId);
        if (permission != null) {
            message.setData(permission);
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
        Page<Permission> page;
        //判断名称是否为空
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        try {
            page = repository.findAll(specification, pageable);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }

        message.setCode(CodeConstant.SUCCESS.getCode());
        message.setData(page);
        return message;
    }


    public RestMessage getPermByRoleId(String roleId) {
        RestMessage message = new RestMessage();
        List<Permission> permission;
        try {
            permission = repository.findPermByRoleId(roleId);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        message.setCode(CodeConstant.SUCCESS.getCode());
        message.setData(permission);
        return message;
    }


    public RestMessage nameList() {
        RestMessage message = new RestMessage();
        List<Permission> permissions;
        try {
            permissions = repository.findNameList();
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        message.setData(permissions);
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }


    public RestMessage getPermByUserId(String userId) {
        RestMessage message = new RestMessage();
        List<Permission> permission;
        try {
            permission = repository.findPermissionsByUserId(userId);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            message.setCode(CodeConstant.FAIL.getCode());
            return message;
        }
        message.setCode(CodeConstant.SUCCESS.getCode());
        message.setData(JSON.toJSONString(permission));
        return message;
    }
}
