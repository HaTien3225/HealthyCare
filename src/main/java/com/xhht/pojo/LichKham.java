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
    private LocalDate ngay;

    @Column(name = "da_kham", nullable = false)
    private Boolean daKham = false;
    
    @Column(name = "created_date",nullable = false)
    private LocalDate createdDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bac_si_id", referencedColumnName = "id")
    private User bacSiId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "khunggio_id", referencedColumnName = "id")
    private KhungGio khungGioId;
    
    @OneToMany(mappedBy = "lichKhamId",cascade = CascadeType.ALL)
    private Set<DonKham> donKhams;
    
    @Column(name = "is_accept",nullable = false)
    private Boolean isAccept;

    @ManyToOne(optional = false)
    @JoinColumn(name = "benh_nhan_id", referencedColumnName = "id")
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
     * @return the ngay
     */
    public LocalDate getNgay() {
        return ngay;
    }

    /**
     * @param ngay the ngay to set
     */
    public void setNgay(LocalDate ngay) {
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

    /**
     * @return the isAccept
     */
    public Boolean getIsAccept() {
        return isAccept;
    }

    /**
     * @param isAccept the isAccept to set
     */
    public void setIsAccept(Boolean isAccept) {
        this.isAccept = isAccept;
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
}
