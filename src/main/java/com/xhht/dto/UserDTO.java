/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.dto;

import com.xhht.pojo.User;
import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author hatie
 */

public class UserDTO {
    private Integer id;
    private String ho;
    private String ten;
    private String username;
    private String password;
    private String email;
    private LocalDate createdDate;
    private String cccd;
    private String phone;
    private boolean isActive;
    private String avatar;
    private Integer khoaId;
    private Integer roleId;
    private MultipartFile file;

    public UserDTO(User u) {
        this.id = u.getId();
        this.ho = u.getHo();
        this.ten = u.getTen();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.createdDate = u.getCreatedDate();
        this.cccd = u.getCccd();
        this.phone = u.getPhone();
        this.isActive = u.isIsActive();
        this.avatar = u.getAvatar();
    }
    
    

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getKhoaId() {
        return khoaId;
    }

    public void setKhoaId(Integer khoaId) {
        this.khoaId = khoaId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

