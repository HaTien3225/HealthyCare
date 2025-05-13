/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "donkham")
public class DonKham implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ghi_chu", columnDefinition = "varchar(2048) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String ghiChu;

    @ManyToOne( optional = false)
    @JoinColumn(name = "benh_id", referencedColumnName = "id")
    private Benh benhId;

    @ManyToOne( optional = false)
    @JoinColumn(name = "hssk_id",referencedColumnName = "id")
    private HoSoSucKhoe hoSoSucKhoeId;

    @ManyToOne( optional = false)
    @JoinColumn(name = "bac_si_id", referencedColumnName = "id")
    private User bacSiId;
    
    @OneToMany(mappedBy = "donKhamId", cascade = CascadeType.ALL)
    private Set<ChiTietDonKham> chiTietDonKhams;
    
    @OneToMany(mappedBy = "donKhamId",cascade = CascadeType.ALL)
    private Set<XetNghiem> xetNghiems;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "lich_kham_id", referencedColumnName = "id")
    private LichKham lichKhamId;
    
    @Column(name = "created_date",nullable = false)
    private LocalDate createdDate;
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
     * @return the ghiChu
     */
    public String getGhiChu() {
        return ghiChu;
    }

    /**
     * @param ghiChu the ghiChu to set
     */
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    /**
     * @return the benhId
     */
    public Benh getBenhId() {
        return benhId;
    }

    /**
     * @param benhId the benhId to set
     */
    public void setBenhId(Benh benhId) {
        this.benhId = benhId;
    }

    /**
     * @return the hoSoSucKhoeId
     */
    public HoSoSucKhoe getHoSoSucKhoeId() {
        return hoSoSucKhoeId;
    }

    /**
     * @param hoSoSucKhoeId the hoSoSucKhoeId to set
     */
    public void setHoSoSucKhoeId(HoSoSucKhoe hoSoSucKhoeId) {
        this.hoSoSucKhoeId = hoSoSucKhoeId;
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
     * @return the chiTietDonKhams
     */
    public Set<ChiTietDonKham> getChiTietDonKhams() {
        return chiTietDonKhams;
    }

    /**
     * @param chiTietDonKhams the chiTietDonKhams to set
     */
    public void setChiTietDonKhams(Set<ChiTietDonKham> chiTietDonKhams) {
        this.chiTietDonKhams = chiTietDonKhams;
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
