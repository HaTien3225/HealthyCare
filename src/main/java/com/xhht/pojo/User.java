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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lehuy
 */
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 225, columnDefinition = "varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String ho;

    @Column(nullable = false, length = 225, columnDefinition = "varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String ten;

    @Column(nullable = false, length = 20,unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 225)
    private String email;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(nullable = false, length = 12)
    private String cccd;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isActive;

    @Column(length = 2083, columnDefinition = "varchar(2083) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String avatar;

    // Quan hệ với Khoa (nhiều User thuộc về 1 Khoa)
    @ManyToOne(optional = true)
    @JoinColumn(name = "khoa_id", referencedColumnName = "id")
    private Khoa khoaId;

    // Quan hệ 1-1 với GiayPhepHanhNghe
    @OneToOne(mappedBy = "bacSiId", cascade = CascadeType.ALL)
    private GiayPhepHanhNghe giayPhepHanhNgheId;
    // Quan hệ với Role (nhiều User cùng 1 Role)
    @ManyToOne( optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role roleId;

    @OneToOne(mappedBy = "benhNhanId", cascade = CascadeType.ALL)
    private HoSoSucKhoe hoSoSucKhoeId;

    @OneToMany(mappedBy = "bacSiId", cascade = CascadeType.ALL)
    private Set<DonKham> donKhams;

    @OneToMany(mappedBy = "bacSiId", cascade = CascadeType.ALL)
    private Set<LichKham> lichKhams;

    @OneToMany(mappedBy = "benhNhanId", cascade = CascadeType.ALL)
    private Set<DanhGia> danhGiasCuaBenhNhans;

    @OneToMany(mappedBy = "bacSiId", cascade = CascadeType.ALL)
    private Set<DanhGia> danhGiasCuaBacSis;

    @OneToMany(mappedBy = "senderId",cascade = CascadeType.ALL)
    private Set<TinNhan> tinNhanGuis;
    
    @OneToMany(mappedBy = "receiverId",cascade = CascadeType.ALL)
    private Set<TinNhan> tinNhanNhans;
    
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
     * @return the ho
     */
    public String getHo() {
        return ho;
    }

    /**
     * @param ho the ho to set
     */
    public void setHo(String ho) {
        this.ho = ho;
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the cccd
     */
    public String getCccd() {
        return cccd;
    }

    /**
     * @param cccd the cccd to set
     */
    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the khoaId
     */
    public Khoa getKhoaId() {
        return khoaId;
    }

    /**
     * @param khoaId the khoaId to set
     */
    public void setKhoaId(Khoa khoaId) {
        this.khoaId = khoaId;
    }

    /**
     * @return the giayPhepHanhNghe
     */
    public GiayPhepHanhNghe getGiayPhepHanhNgheId() {
        return giayPhepHanhNgheId;
    }

    /**
     * @param giayPhepHanhNghe the giayPhepHanhNghe to set
     */
    public void setGiayPhepHanhNgheId(GiayPhepHanhNghe giayPhepHanhNgheId) {
        this.giayPhepHanhNgheId = giayPhepHanhNgheId;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return roleId;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role roleId) {
        this.roleId = roleId;
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

    /**
     * @return the lichKhams
     */
    public Set<LichKham> getLichKhams() {
        return lichKhams;
    }

    /**
     * @param lichKhams the lichKhams to set
     */
    public void setLichKhams(Set<LichKham> lichKhams) {
        this.lichKhams = lichKhams;
    }

    /**
     * @return the danhGiasCuaBenhNhans
     */
    public Set<DanhGia> getDanhGiasCuaBenhNhans() {
        return danhGiasCuaBenhNhans;
    }

    /**
     * @param danhGiasCuaBenhNhans the danhGiasCuaBenhNhans to set
     */
    public void setDanhGiasCuaBenhNhans(Set<DanhGia> danhGiasCuaBenhNhans) {
        this.danhGiasCuaBenhNhans = danhGiasCuaBenhNhans;
    }

    /**
     * @return the danhGiasCuaBacSis
     */
    public Set<DanhGia> getDanhGiasCuaBacSis() {
        return danhGiasCuaBacSis;
    }

    /**
     * @param danhGiasCuaBacSis the danhGiasCuaBacSis to set
     */
    public void setDanhGiasCuaBacSis(Set<DanhGia> danhGiasCuaBacSis) {
        this.danhGiasCuaBacSis = danhGiasCuaBacSis;
    }

    /**
     * @return the tinNhanGuis
     */
    public Set<TinNhan> getTinNhanGuis() {
        return tinNhanGuis;
    }

    /**
     * @param tinNhanGuis the tinNhanGuis to set
     */
    public void setTinNhanGuis(Set<TinNhan> tinNhanGuis) {
        this.tinNhanGuis = tinNhanGuis;
    }

    /**
     * @return the tinNhanNhans
     */
    public Set<TinNhan> getTinNhanNhans() {
        return tinNhanNhans;
    }

    /**
     * @param tinNhanNhans the tinNhanNhans to set
     */
    public void setTinNhanNhans(Set<TinNhan> tinNhanNhans) {
        this.tinNhanNhans = tinNhanNhans;
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
