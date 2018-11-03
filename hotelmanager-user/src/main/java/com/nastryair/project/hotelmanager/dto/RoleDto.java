package com.nastryair.project.hotelmanager.dto;

/**
 * Created by QWL on 2018/4/16.
 */
public class RoleDto {

    private Integer id;
    private String roleId;
    private String name;

    public RoleDto(Integer id, String roleId, String name) {
        this.id = id;
        this.roleId = roleId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
