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
import java.util.Set;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "xetnghiem")
public class XetNghiem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "mo_ta", nullable = false, length = 255)
    private String moTa;
    @ManyToOne(optional = false)
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

    /**
     * @return the chiTietXetNghiems
     */
    public Set<ChiTietXetNghiem> getChiTietXetNghiems() {
        return chiTietXetNghiems;
    }

    /**
     * @param chiTietXetNghiems the chiTietXetNghiems to set
     */
    public void setChiTietXetNghiems(Set<ChiTietXetNghiem> chiTietXetNghiems) {
        this.chiTietXetNghiems = chiTietXetNghiems;
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

}
