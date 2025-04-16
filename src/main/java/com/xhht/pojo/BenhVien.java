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
@Table(name = "benhvien")
public class BenhVien implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ngay_thanh_lap", nullable = false)
    private LocalDate ngayThanhLap;

    @Column(name = "ten_benh_vien", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String tenBenhVien;

    @Column(name = "gioi_thieu", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String gioiThieu;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;
    @OneToMany(mappedBy = "khoaId",cascade = CascadeType.ALL)
    private Set<Khoa> khoas;

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
     * @return the ngayThanhLap
     */
    public LocalDate getNgayThanhLap() {
        return ngayThanhLap;
    }

    /**
     * @param ngayThanhLap the ngayThanhLap to set
     */
    public void setNgayThanhLap(LocalDate ngayThanhLap) {
        this.ngayThanhLap = ngayThanhLap;
    }

    /**
     * @return the tenBenhVien
     */
    public String getTenBenhVien() {
        return tenBenhVien;
    }

    /**
     * @param tenBenhVien the tenBenhVien to set
     */
    public void setTenBenhVien(String tenBenhVien) {
        this.tenBenhVien = tenBenhVien;
    }

    /**
     * @return the gioiThieu
     */
    public String getGioiThieu() {
        return gioiThieu;
    }

    /**
     * @param gioiThieu the gioiThieu to set
     */
    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    /**
     * @return the diaChi
     */
    public String getDiaChi() {
        return diaChi;
    }

    /**
     * @param diaChi the diaChi to set
     */
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    /**
     * @return the khoas
     */
    public Set<Khoa> getKhoas() {
        return khoas;
    }

    /**
     * @param khoas the khoas to set
     */
    public void setKhoas(Set<Khoa> khoas) {
        this.khoas = khoas;
    }
}
