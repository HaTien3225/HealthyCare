/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "Danhgia")
public class DanhGia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rating", nullable = false, precision = 1, scale = 0)
    private BigDecimal rating;

    @Column(name = "binh_luan", length = 255)
    private String binhLuan;

    @Column(name = "phan_hoi", length = 255)
    private String phanHoi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "benh_nhan_id", referencedColumnName = "id")
    private User benhNhanId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bac_si_id", referencedColumnName = "id")
    private User bacSiId;

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
     * @return the rating
     */
    public BigDecimal getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    /**
     * @return the binhLuan
     */
    public String getBinhLuan() {
        return binhLuan;
    }

    /**
     * @param binhLuan the binhLuan to set
     */
    public void setBinhLuan(String binhLuan) {
        this.binhLuan = binhLuan;
    }

    /**
     * @return the phanHoi
     */
    public String getPhanHoi() {
        return phanHoi;
    }

    /**
     * @param phanHoi the phanHoi to set
     */
    public void setPhanHoi(String phanHoi) {
        this.phanHoi = phanHoi;
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

    /**
     * @return the bacSiId
     */
    public User getBacSiId() {
        return bacSiId;
    }

    /**
     * @param bacSiId the bacSiId to set
     */
    public void setBacSiId(User bacSiId) {
        this.bacSiId = bacSiId;
    }
}
