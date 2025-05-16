/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.dto.HoaDonThanhToanDTO;
import com.xhht.pojo.ChiTietDonKham;
import com.xhht.pojo.DonKham;
import com.xhht.pojo.User;
import com.xhht.pojo.XetNghiem;
import com.xhht.services.DonKhamService;
import com.xhht.services.UserService;
import com.xhht.services.XetNghiemService;
import com.xhht.vnpay.Vnpay;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiPatientDonKhamController {

    @Autowired
    private DonKhamService donKhamService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private XetNghiemService xetNghiemService;

    @GetMapping("/donkham")
    public ResponseEntity<?> listDonKham(@RequestParam(name = "kw", required = false) String kw,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "date", required = false) LocalDate date,
            Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        return ResponseEntity.ok(this.donKhamService.getAllDonKham(u.getId(), true, page, pageSize, kw, date));
    }

    @GetMapping("/donkham/{id}")
    public ResponseEntity<?> getDonKham(@PathVariable(name = "id") int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }

        Optional<DonKham> dk = this.donKhamService.getDonKham(id);
        if (dk.get().getHoSoSucKhoeId().getBenhNhanId().getId() != u.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        return ResponseEntity.ok(dk);
    }

    @GetMapping("/chitietdonkham")
    public ResponseEntity<?> getChiTietDonKham(@RequestParam(name = "donKhamId", required = true) int donKhamId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<DonKham> dk = this.donKhamService.getDonKham(donKhamId);
        if (dk.get().getHoSoSucKhoeId().getBenhNhanId().getId() != u.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        List<ChiTietDonKham> chiTietList = this.donKhamService.getAllChiTietDonKham(donKhamId);
        return ResponseEntity.ok(chiTietList);
    }

    @GetMapping("/xetnghiem")
    public ResponseEntity<?> getXetNghiem(@RequestParam(name = "donKhamId") int donKhamId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        
        Optional<DonKham> dk = this.donKhamService.getDonKham(donKhamId);
        if (dk.get().getHoSoSucKhoeId().getBenhNhanId().getId() != u.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }

        List<XetNghiem> xetNghiemList = this.xetNghiemService.getALlXetNghiem(donKhamId);
        return ResponseEntity.ok(xetNghiemList);
    }
    
    
    @GetMapping("/thanhtoan")
    public ResponseEntity<?> thanhToan(@RequestParam(name = "donKhamId",required = true) Integer donKhamId,Principal principal,HttpServletRequest req) throws UnsupportedEncodingException{
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        
        Optional<DonKham> dk = this.donKhamService.getDonKham(donKhamId);
        if (dk.get().getHoSoSucKhoeId().getBenhNhanId().getId() != u.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if(dk.get().getIsPaid()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN isPaid = true");
        }
        BigDecimal price = this.donKhamService.getDonKhamPrice(donKhamId);
        Vnpay vnpay = new Vnpay();
        String vnPayURL = vnpay.vnPayURLInit(price.doubleValue(),req,donKhamId);
        
        HoaDonThanhToanDTO hdttdto = new HoaDonThanhToanDTO(donKhamId, dk.get().getGhiChu(), price, vnPayURL);
        return ResponseEntity.ok(hdttdto);      
    }

}
