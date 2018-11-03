package com.nastryair.project.hotelmanager.dto;

/**
 * UserPasswordDto class
 *
 * @author QWL
 * @date 2018/7/27
 */
public class UserPasswordDto {
    private String userId;
    private String password;
    private String newPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
