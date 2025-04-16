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
import java.time.LocalDate;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "Benh")
public class Benh implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "ten_benh", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String tenBenh;

    @Column(name = "mo_ta", columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String moTa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "khoa_id", referencedColumnName = "id")
    private Khoa khoa;
}
