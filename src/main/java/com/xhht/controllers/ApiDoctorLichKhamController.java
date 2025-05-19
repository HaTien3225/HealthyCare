package com.xhht.controllers;

import com.xhht.pojo.*;
import com.xhht.services.BenhService;
import com.xhht.services.XetNghiemService;
import com.xhht.services.DonKhamService;
import com.xhht.services.EmailService;
import com.xhht.services.LichKhamService;
import com.xhht.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class ApiDoctorLichKhamController {

    @Autowired
    private LichKhamService lichKhamService;

    @Autowired
    private DonKhamService donKhamService;

    @Autowired
    private BenhService benhService;

    @Autowired
    private XetNghiemService xetNghiemService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    private User getCurrentDoctor(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }

    @GetMapping("/lichkham/pending")
    public ResponseEntity<?> getPendingLichKham(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        User doctor = getCurrentDoctor(principal);
        List<LichKham> lichkhams = lichKhamService.getLichKhamByBacSiChuaKham(doctor.getId());
        return ResponseEntity.ok(lichkhams);
    }

    @PutMapping("/lichkham/{id}/accept")
    public ResponseEntity<?> acceptLichKham(@PathVariable("id") int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<LichKham> optional = lichKhamService.getLichKhamById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setIsAccept(true);

            emailService.sendSimpleEmail(
                    lichkham.getBenhNhanId().getEmail(),
                    "Lịch khám được chấp nhận",
                    "Bác sĩ đã chấp nhận lịch khám của bạn vào ngày " + lichkham.getNgay()
            );
            return ResponseEntity.ok(lichKhamService.saveLichKham(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    @PutMapping("/lichkham/{id}/reject")
    public ResponseEntity<?> rejectLichKham(@PathVariable("id") int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<LichKham> optional = lichKhamService.getLichKhamById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setIsAccept(false);

            emailService.sendSimpleEmail(
                    lichkham.getBenhNhanId().getEmail(),
                    "Lịch khám bị từ chối",
                    "Rất tiếc, lịch khám của bạn vào ngày " + lichkham.getNgay() + " đã bị từ chối."
            );
            return ResponseEntity.ok(lichKhamService.saveLichKham(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    @GetMapping("/lichkham/accepted")
    public ResponseEntity<?> getAcceptedLichKham(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        User doctor = getCurrentDoctor(principal);
        List<LichKham> lichkhams = lichKhamService.getLichKhamDaDuyetChuaKham(doctor.getId());
        return ResponseEntity.ok(lichkhams);
    }

    @PutMapping("/lichkham/{id}/update-status")
    public ResponseEntity<?> updateLichKhamStatus(@PathVariable("id") int id, @RequestBody LichKham lichkhamDetails, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<LichKham> optional = lichKhamService.getLichKhamById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setDaKham(lichkhamDetails.getDaKham());
            return ResponseEntity.ok(lichKhamService.saveLichKham(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    @PostMapping("/lichkham/{id}/donkham")
    public ResponseEntity<?> createDonKham(@PathVariable("id") int lichKhamId, @RequestBody DonKham donKhamRequest, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<LichKham> lichKhamOpt = lichKhamService.getLichKhamById(lichKhamId);
        if (lichKhamOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy lịch khám!");
        }

        LichKham lichKham = lichKhamOpt.get();

        if (!Boolean.TRUE.equals(lichKham.getIsAccept())) {
            return ResponseEntity.badRequest().body("Lịch khám chưa được bác sĩ duyệt!");
        }

        User doctor = getCurrentDoctor(principal);

        donKhamRequest.setBacSiId(doctor);
        donKhamRequest.setIsPaid(false);
        donKhamRequest.setHoSoSucKhoeId(lichKham.getBenhNhanId().getHoSoSucKhoeId());
        donKhamRequest.setLichKhamId(lichKham);
        donKhamRequest.setCreatedDate(LocalDate.now());

        DonKham savedDonKham = donKhamService.save(donKhamRequest);

        return ResponseEntity.ok(savedDonKham);
    }

    @PostMapping("/donkham/{id}/xetnghiem")
    public ResponseEntity<?> addXetNghiem(@PathVariable("id") int donKhamId, @RequestBody XetNghiem xetNghiemRequest, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<DonKham> donKhamOpt = donKhamService.getDonKham(donKhamId);
        if (donKhamOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn khám!");
        }

        DonKham donKham = donKhamOpt.get();
        xetNghiemRequest.setDonKhamId(donKham);

        XetNghiem savedXetNghiem = xetNghiemService.save(xetNghiemRequest);

        return ResponseEntity.ok(savedXetNghiem);
    }

    @GetMapping("/benh/search")
    public ResponseEntity<?> searchBenh(@RequestParam("keyword") String keyword, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        List<Benh> benhList = benhService.findByTenBenh(keyword);
        return ResponseEntity.ok(benhList);
    }

    @PostMapping("/donkham/{id}/benh/{benhId}")
    public ResponseEntity<?> assignBenhToDonKham(@PathVariable("id") int donKhamId, @PathVariable("benhId") int benhId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<DonKham> donKhamOpt = donKhamService.getDonKham(donKhamId);
        Benh benhOpt = benhService.getBenhById(benhId);

        if (donKhamOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn khám!");
        }

        if (benhOpt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bệnh!");
        }

        DonKham donKham = donKhamOpt.get();
        donKham.setBenhId(benhOpt);
        donKhamService.save(donKham);

        return ResponseEntity.ok(donKham);
    }

    @GetMapping("/donkham/{donKhamId}/benh")
    public ResponseEntity<?> getBenhByDonKham(@PathVariable("donKhamId") Long donKhamId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<Benh> benhOpt = donKhamService.getBenhByDonKham(donKhamId);
        if (benhOpt.isPresent()) {
            return ResponseEntity.ok(benhOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy bệnh cho đơn khám id = " + donKhamId);
        }
    }

    @PostMapping("/lichkham/{id}/send-invite")
    public ResponseEntity<?> sendTuvanInvite(@PathVariable("id") int id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<LichKham> lichKham = lichKhamService.getLichKhamById(id);
        String email = lichKham.get().getBenhNhanId().getEmail();

        String roomId = "tu-van-" + id;
        String link = "https://meet.jit.si/" + roomId;

        emailService.sendInviteEmail(email, link);

        return ResponseEntity.ok("Email mời đã được gửi.");
    }

}
