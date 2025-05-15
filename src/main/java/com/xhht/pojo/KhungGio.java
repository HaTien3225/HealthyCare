/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "khunggio")
public class KhungGio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_kg", nullable = false, length = 20)
    private String tenKg;

    @Column(name = "gio_bat_dau", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "gio_ket_thuc", nullable = false)
    private LocalTime gioKetThuc;
    
    @JsonIgnore
    @OneToMany(mappedBy = "khungGioId",cascade = CascadeType.ALL)
    private Set<LichKham> lichKhams;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the tenKg
     */
    public String getTenKg() {
        return tenKg;
    }

    /**
     * @param tenKg the tenKg to set
     */
    public void setTenKg(String tenKg) {
        this.tenKg = tenKg;
    }

    /**
     * @return the gioBatDau
     */
    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    /**
     * @param gioBatDau the gioBatDau to set
     */
    public void setGioBatDau(LocalTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    /**
     * @return the gioKetThuc
     */
    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    /**
     * @param gioKetThuc the gioKetThuc to set
     */
    public void setGioKetThuc(LocalTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    /**
     * @return the lichKhams
     */
    public Set<LichKham> getLichKhams() {
        return lichKhams;
    }

    /**
     * @param lichKhams the lichKhams to set
     */
    public void setLichKhams(Set<LichKham> lichKhams) {
        this.lichKhams = lichKhams;
    }
}
