package com.nastryair.project.hotelmanager.controller.impl;


import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.controller.UserApi;
import com.nastryair.project.hotelmanager.entity.User;
import com.nastryair.project.hotelmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhh [zhouhaihong@youedata.com].
 * @date 2018/4/9
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController implements UserApi {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody User user) {
        RestMessage message = userService.create(user);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public ResponseEntity createRole(@RequestBody String userRole) {
        RestMessage message = userService.createRole(userRole);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity drop(@PathVariable(value = "userId") String userId) {
        RestMessage message = userService.drop(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity alter(@RequestBody String user) {
        RestMessage message = userService.alter(user);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public ResponseEntity alterPassword(@RequestBody String user) {
        RestMessage message = userService.alterPassword(user);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/uploadavatar", method = RequestMethod.POST)
    public ResponseEntity alterAvatar(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam String userId) {
        RestMessage message = userService.alterAvatar(multipartFile, userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/avatar", method = RequestMethod.GET)
    public void getAvatar(@RequestParam String userId, HttpServletResponse response) {
        userService.getAvatar(userId, response);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(required = false) String userName,
                               @RequestParam(required = false, defaultValue = "ASC") String order,
                               @RequestParam(required = false, defaultValue = "id") String orderColumn,
                               @RequestParam(required = false, defaultValue = "0") String currentPage,
                               @RequestParam(required = false, defaultValue = "10") String pageSize) {
        RestMessage message = userService.list(userName, order, orderColumn, currentPage, pageSize);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity userInfo(@PathVariable String userId) {
        RestMessage message = userService.userInfo(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public ResponseEntity userInfoByRoleId(@PathVariable String roleId) {
        RestMessage message = userService.getByRoleId(roleId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/role/name/{name}", method = RequestMethod.GET)
    public ResponseEntity userInfoByRoleName(@PathVariable String name) {
        RestMessage message = userService.getByRoleName(name);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity findByName(@RequestParam String username) {
        RestMessage message = userService.findUserByName(username);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{userId}/role", method = RequestMethod.GET)
    public ResponseEntity roleInfo(@PathVariable String userId) {
        RestMessage message = userService.roleInfo(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @Override
//    @RequestMapping(value = "/find/accessid/{accessId}", method = RequestMethod.GET)
//    public ResponseEntity findByAccessId(
//            @PathVariable String accessId) {
//        RestMessage message = userService.findByAccessId(accessId);
//        return new ResponseEntity<>(message, HttpStatus.OK);
//    }


}
