package com.nastryair.project.hotelmanager.dto;

import java.util.List;

/**
 * Created by QWL on 2018/4/20.
 */
public class RoleIdPermissionIdsDto {
    private String roleId;
    private List<String> permissionIdList;

    public RoleIdPermissionIdsDto() {
    }

    public RoleIdPermissionIdsDto(String roleId, List<String> permissionIdList) {
        this.roleId = roleId;
        this.permissionIdList = permissionIdList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getPermissionIdList() {
        return permissionIdList;
    }

    public void setPermissionIdList(List<String> permissionIdList) {
        this.permissionIdList = permissionIdList;
    }
}
