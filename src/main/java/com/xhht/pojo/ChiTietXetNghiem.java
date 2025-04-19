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
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "Chitietxetnghiem")
public class ChiTietXetNghiem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mo_ta", nullable = false, length = 255)
    private String moTa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "xet_nghiem_id", referencedColumnName = "id")
    private XetNghiem xetNghiemId;

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
     * @return the xetNghiemId
     */
    public XetNghiem getXetNghiemId() {
        return xetNghiemId;
    }

    /**
     * @param xetNghiemId the xetNghiemId to set
     */
    public void setXetNghiemId(XetNghiem xetNghiemId) {
        this.xetNghiemId = xetNghiemId;
    }
}
