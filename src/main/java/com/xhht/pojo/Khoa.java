/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "khoa")
public class Khoa implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "ten_khoa", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String tenKhoa;

    @Column(name = "mo_ta", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String moTa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "benh_vien_id",referencedColumnName = "id")
    private BenhVien benhvien;
    
    @OneToMany(mappedBy = "benhId", cascade = CascadeType.ALL)
    private Set<Benh> benhs;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the createdDate
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the tenKhoa
     */
    public String getTenKhoa() {
        return tenKhoa;
    }

    /**
     * @param tenKhoa the tenKhoa to set
     */
    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    /**
     * @return the moTa
     */
    public String getMoTa() {
        return moTa;
    }

    /**
     * @param moTa the moTa to set
     */
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    /**
     * @return the benhvien
     */
    public BenhVien getBenhvien() {
        return benhvien;
    }

    /**
     * @param benhvien the benhvien to set
     */
    public void setBenhvien(BenhVien benhvien) {
        this.benhvien = benhvien;
    }
}
