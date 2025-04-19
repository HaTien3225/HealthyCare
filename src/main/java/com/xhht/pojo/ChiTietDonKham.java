/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "chitietdonkham")
public class ChiTietDonKham implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dich_vu", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String dichVu;

    @Column(name = "gia_tien", precision = 10, scale = 2)
    private BigDecimal giaTien;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "don_kham_id", referencedColumnName = "id")
    private DonKham donKhamId;

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
     * @return the dichVu
     */
    public String getDichVu() {
        return dichVu;
    }

    /**
     * @param dichVu the dichVu to set
     */
    public void setDichVu(String dichVu) {
        this.dichVu = dichVu;
    }

    /**
     * @return the giaTien
     */
    public BigDecimal getGiaTien() {
        return giaTien;
    }

    /**
     * @param giaTien the giaTien to set
     */
    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    /**
     * @return the donKhamId
     */
    public DonKham getDonKhamId() {
        return donKhamId;
    }

    /**
     * @param donKhamId the donKhamId to set
     */
    public void setDonKhamId(DonKham donKhamId) {
        this.donKhamId = donKhamId;
    }
    
}
