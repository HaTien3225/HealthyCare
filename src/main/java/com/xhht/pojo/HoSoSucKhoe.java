/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "hososuckhoe")
public class HoSoSucKhoe implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chieu_cao")
    private Integer chieuCao;

    @Column(name = "can_nang")
    private Integer canNang;

    @Column(name = "birth")
    private LocalDate birth;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "benh_nhan_id", referencedColumnName = "id", nullable = false, unique = true)
    private User benhNhanId;

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
     * @return the chieuCao
     */
    public Integer getChieuCao() {
        return chieuCao;
    }

    /**
     * @param chieuCao the chieuCao to set
     */
    public void setChieuCao(Integer chieuCao) {
        this.chieuCao = chieuCao;
    }

    /**
     * @return the canNang
     */
    public Integer getCanNang() {
        return canNang;
    }

    /**
     * @param canNang the canNang to set
     */
    public void setCanNang(Integer canNang) {
        this.canNang = canNang;
    }

    /**
     * @return the birth
     */
    public LocalDate getBirth() {
        return birth;
    }

    /**
     * @param birth the birth to set
     */
    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    /**
     * @return the benhNhanId
     */
    public User getBenhNhanId() {
        return benhNhanId;
    }

    /**
     * @param benhNhanId the benhNhanId to set
     */
    public void setBenhNhanId(User benhNhanId) {
        this.benhNhanId = benhNhanId;
    }
}
