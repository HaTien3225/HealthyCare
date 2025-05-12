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
import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "lichkham")
public class LichKham implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ngay", nullable = false)
    private LocalDateTime ngay;

    @Column(name = "da_kham", nullable = false)
    private Boolean daKham = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bac_si_id", referencedColumnName = "id")
    private User bacSiId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "khunggio_id", referencedColumnName = "id")
    private KhungGio khungGioId;
    
    @OneToMany(mappedBy = "lichKhamId",cascade = CascadeType.ALL)
    private Set<DonKham> donKhams;

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
     * @return the ngay
     */
    public LocalDateTime getNgay() {
        return ngay;
    }

    /**
     * @param ngay the ngay to set
     */
    public void setNgay(LocalDateTime ngay) {
        this.ngay = ngay;
    }

    /**
     * @return the daKham
     */
    public Boolean getDaKham() {
        return daKham;
    }

    /**
     * @param daKham the daKham to set
     */
    public void setDaKham(Boolean daKham) {
        this.daKham = daKham;
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

    /**
     * @return the khungGioId
     */
    public KhungGio getKhungGio() {
        return khungGioId;
    }

    /**
     * @param khungGioId the khungGio to set
     */
    public void setKhungGio(KhungGio khungGioId) {
        this.khungGioId = khungGioId;
    }
}
