/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Benh")
public class Benh implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "ten_benh", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String tenBenh;

    @Column(name = "mo_ta", columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String moTa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "khoa_id", referencedColumnName = "id")
    private Khoa khoaId;
    @JsonIgnore
    @OneToMany(mappedBy = "benhId",cascade = CascadeType.ALL)
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

    /**
     * @return the tenBenh
     */
    public String getTenBenh() {
        return tenBenh;
    }

    /**
     * @param tenBenh the tenBenh to set
     */
    public void setTenBenh(String tenBenh) {
        this.tenBenh = tenBenh;
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
     * @return the khoaId
     */
    @JsonIgnore
    public Khoa getKhoa() {
        return khoaId;
    }

    /**
     * @param khoaId the khoa to set
     */
    public void setKhoa(Khoa khoaId) {
        this.khoaId = khoaId;
    }

    /**
     * @return the donKhams
     */
    public Set<DonKham> getDonKhams() {
        return donKhams;
    }

    /**
     * @param donKhams the donKhams to set
     */
    public void setDonKhams(Set<DonKham> donKhams) {
        this.donKhams = donKhams;
    }
}
