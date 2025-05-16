/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.dto;

import java.math.BigDecimal;

/**
 *
 * @author lehuy
 */
public class HoaDonThanhToanDTO {

    private Integer donKhamId;
    private String ghiChu;
    private BigDecimal totalPrice;
    private String vnPayURL;

    public HoaDonThanhToanDTO(Integer donKhamId,
            String ghiChu,
            BigDecimal totalPrice,
            String vnPayURL ) {
        this.donKhamId = donKhamId;
        this.ghiChu = ghiChu;
        this.totalPrice = totalPrice;
        this.vnPayURL = vnPayURL;
    }

    /**
     * @return the donKhamId
     */
    public Integer getDonKhamId() {
        return donKhamId;
    }

    /**
     * @param donKhamId the donKhamId to set
     */
    public void setDonKhamId(Integer donKhamId) {
        this.donKhamId = donKhamId;
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
     * @return the totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the vnPayURL
     */
    public String getVnPayURL() {
        return vnPayURL;
    }

    /**
     * @param vnPayURL the vnPayURL to set
     */
    public void setVnPayURL(String vnPayURL) {
        this.vnPayURL = vnPayURL;
    }
}
