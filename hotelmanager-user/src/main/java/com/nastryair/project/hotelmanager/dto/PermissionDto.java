package com.nastryair.project.hotelmanager.dto;

/**
 * Created by QWL on 2018/4/16.
 */
public class PermissionDto {

    private Integer id;
    private String permissionId;
    private String name;

    public PermissionDto(Integer id, String permissionId, String name) {
        this.id = id;
        this.permissionId = permissionId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
