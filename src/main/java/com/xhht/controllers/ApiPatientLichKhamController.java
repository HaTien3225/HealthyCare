/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.LichKham;
import com.xhht.pojo.User;
import com.xhht.repositories.LichKhamRepository;
import com.xhht.services.KhungGioService;
import com.xhht.services.LichKhamService;
import com.xhht.services.MailSenderService;
import com.xhht.services.UserService;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hatie
 */
@RestController
@CrossOrigin
public class ApiPatientLichKhamController {

    @Autowired
    private UserService userService;

    @Autowired
    private KhungGioService khungGioService;

    @Autowired
    private LichKhamService lichKhamService;
    
    @Autowired
    private MailSenderService mailSenderService;
    


    
    @GetMapping("/api/lichkham")
    public ResponseEntity<?> getLichKhamForPatient(Principal principal, @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam (name = "isAccept",required = false) Boolean isAccept,
            @RequestParam (name = "daKham",required = false) Boolean daKham) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        List<LichKham> lichkhams = lichKhamService.getLichKhamByBenhNhan(u.getId().longValue(),isAccept,daKham,page);
        return ResponseEntity.ok(lichkhams);
    }

    // Cập nhật trạng thái lịch khám (đã khám hoặc chưa) ko can sua lichkham o user dau ong
//    @PutMapping("/api/lichkham/{id}")
//    public ResponseEntity<?> updateLichKham(@PathVariable int id, @RequestBody LichKham lichkhamDetails,Principal principal) {
//        if(principal == null)
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
//        User u = this.userService.getUserByUsername(principal.getName());
//        if(!u.getRole().getRole().equals("ROLE_USER"))
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
//        Optional<LichKham> optionalLichKham = lichKhamRepository.findById(id);
//
//        if (optionalLichKham.isPresent()) {
//            LichKham lichKham = optionalLichKham.get();
//            lichKham.setDaKham(lichkhamDetails.getDaKham());
//            // Cập nhật các trường khác nếu cần
//            return ResponseEntity.ok(lichKhamRepository.save(lichKham));
//        } else {
//            return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
//        }
//    }
    @PostMapping("/api/lichkham")
    public ResponseEntity<?> createLichKham(@RequestBody LichKham lichKham, Principal principal, @RequestParam(name = "bacSiId", required = true) int bacSiId,
            @RequestParam(name = "khungGioId", required = true) int khungGioId) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }

        lichKham.setBacSiId(this.userService.getUserById(bacSiId));
        lichKham.setBenhNhanId(u);
        lichKham.setCreatedDate(LocalDate.now());
        lichKham.setKhungGio(this.khungGioService.findKhungGioById(khungGioId));
        lichKham.setDaKham(Boolean.FALSE);
        lichKham.setIsAccept(Boolean.FALSE);

        if (!this.lichKhamService.checkTrungLichKham(lichKham)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Lich kham bi trung");        
        }
        if(lichKham.getNgay().isBefore(LocalDate.now()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NGAY QUA KHU");    
        LichKham saved = lichKhamService.saveLichKham(lichKham);
        
        String mailBody = "Đã tạo yêu cầu đặt lịch khám với bác sĩ "+lichKham.getBacSiId().getHo() + " "+lichKham.getBacSiId().getTen()
                +"vào ngày "+ lichKham.getNgay()+" khung giờ "+ lichKham.getKhungGio().getTenKg()+", "+"vui lòng đợi phản hồi của bác sĩ.";
        
        mailSenderService.sendEmail(u.getEmail(), "Thông Báo Đã Tạo Yêu Cầu Đặt Lịch Khám Bệnh ", mailBody);
        
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/api/lichkham/{id}")
    public ResponseEntity<?> cancelLichKham(@PathVariable(name = "id") int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<LichKham> optional = lichKhamService.getLichKhamById(id);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            if (!Objects.equals(lichKham.getBenhNhanId().getId(), u.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
            }
            if(ChronoUnit.DAYS.between(lichKham.getCreatedDate(),LocalDate.now()) > 1)
                return ResponseEntity.status(400).body("Đã qua 24 hours");
            if (!lichKham.getDaKham()) {
                lichKhamService.deleteLichKham(lichKham);
                return ResponseEntity.ok("Đã hủy lịch khám.");
            } else {
                return ResponseEntity.status(400).body("Không thể hủy vì lịch khám đã diễn ra.");
            }
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy lịch khám.");
        }
    }

    @GetMapping("/api/lichkham/{id}")
    public ResponseEntity<?> getLichKhamById(@PathVariable("id") int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User currentUser = this.userService.getUserByUsername(principal.getName());
        if (!currentUser.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<LichKham> optional = this.lichKhamService.getLichKhamById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy lịch khám.");
        }

        LichKham lichKham = optional.get();

        if (!lichKham.getBenhNhanId().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xem lịch khám này.");
        }

        return ResponseEntity.ok(lichKham);
    }

}
