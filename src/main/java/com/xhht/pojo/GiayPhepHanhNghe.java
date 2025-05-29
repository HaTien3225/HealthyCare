 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "giayphephanhnghe")
public class GiayPhepHanhNghe implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private LocalDate created_date;
    @Column(name = "image")
    private String image;
    @Column(name = "is_valid")
    private boolean isValid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "bac_si_id",unique = true,referencedColumnName = "id")
    private User bacSiId;

    @Transient
    private MultipartFile file;
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
     * @return the created_date
     */
    public LocalDate getCreated_date() {
        return created_date;
    }

    /**
     * @param created_date the created_date to set
     */
    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the isValid
     */
    public boolean isIsValid() {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
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
