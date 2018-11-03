package com.nastryair.project.hotelmanager.controller.impl;

import com.alibaba.fastjson.JSON;
import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.controller.RoleApi;
import com.nastryair.project.hotelmanager.dto.RoleIdPermissionIdsDto;
import com.nastryair.project.hotelmanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author QWL
 * @date 2018/4/12
 */
@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController implements RoleApi {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public ResponseEntity info(@PathVariable String roleId) {
        RestMessage message = roleService.info(roleId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "ASC") String order,
            @RequestParam(required = false, defaultValue = "id") String orderColumn,
            @RequestParam(required = false, defaultValue = "0") String currentPage,
            @RequestParam(required = false, defaultValue = "10") String pageSize) {
        RestMessage message = roleService.list(name, order, orderColumn, currentPage, pageSize);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody String role) {
        RestMessage message = roleService.create(role);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity alter(@RequestBody String role) {
        RestMessage message = roleService.alter(role);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public ResponseEntity drop(@PathVariable String roleId) {
        RestMessage message = roleService.drop(roleId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public ResponseEntity addRolePerms(@RequestBody String json) {
        RoleIdPermissionIdsDto roleIdPermissionIdsDto = JSON.parseObject(json, RoleIdPermissionIdsDto.class);
        RestMessage message = roleService.addRolePerms(roleIdPermissionIdsDto.getRoleId(), roleIdPermissionIdsDto.getPermissionIdList());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public ResponseEntity nameList() {
        RestMessage message = roleService.nameList();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity getByUserId(@PathVariable String userId) {
        RestMessage message = roleService.getByUserId(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/byname/{name}", method = RequestMethod.GET)
    public ResponseEntity getByRoleName(
            @PathVariable String name) {
        RestMessage message = roleService.getByRoleName(name);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
