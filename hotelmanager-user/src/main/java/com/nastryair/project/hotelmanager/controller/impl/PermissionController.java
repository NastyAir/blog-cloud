package com.nastryair.project.hotelmanager.controller.impl;


import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.controller.PermissionApi;
import com.nastryair.project.hotelmanager.service.PermissionService;
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
@RequestMapping("/permission")
public class PermissionController implements PermissionApi {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    @RequestMapping(value = "/{permissionId}", method = RequestMethod.GET)
    public ResponseEntity info(@PathVariable String permissionId) {

        RestMessage message = permissionService.info(permissionId);
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
        RestMessage message = permissionService.list(name, order, orderColumn, currentPage, pageSize);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public ResponseEntity getPermByRoleId(@PathVariable String roleId) {
        RestMessage message = permissionService.getPermByRoleId(roleId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity getPermByUserId(@PathVariable String userId) {
        RestMessage message = permissionService.getPermByUserId(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public ResponseEntity nameList() {
        RestMessage message = permissionService.nameList();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
