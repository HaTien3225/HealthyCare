package com.xhht.controllers;

import com.xhht.pojo.*;
import com.xhht.repositories.*;
import com.xhht.services.EmailService;
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
    private LichKhamRepository lichkhamRepository;

    @Autowired
    private DonKhamRepository donKhamRepository;

    @Autowired
    private BenhRepository benhRepository;

    @Autowired
    private XetNghiemRepository xetNghiemRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    // Lấy thông tin bác sĩ từ Principal
    private User getCurrentDoctor(Principal principal) {
        return userRepository.getUserByUsername(principal.getName());
    }

    // 1. Lấy danh sách lịch khám đang chờ bác sĩ chấp nhận
    @GetMapping("/lichkham/pending")
    public ResponseEntity<?> getPendingLichKham(Principal principal) {
        User doctor = getCurrentDoctor(principal);
        List<LichKham> lichkhams = lichkhamRepository.findByBacSiIdAndIsAcceptFalse(doctor.getId());
        return ResponseEntity.ok(lichkhams);
    }

    // 2. Chấp nhận lịch khám
    @PutMapping("/lichkham/{id}/accept")
    public ResponseEntity<?> acceptLichKham(@PathVariable("id") int id) {
        Optional<LichKham> optional = lichkhamRepository.findById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setIsAccept(true);

            emailService.sendSimpleEmail(
                    lichkham.getBenhNhanId().getEmail(),
                    "Lịch khám được chấp nhận",
                    "Bác sĩ đã chấp nhận lịch khám của bạn vào ngày " + lichkham.getNgay()
            );
            return ResponseEntity.ok(lichkhamRepository.save(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    // 3. Từ chối lịch khám
    @PutMapping("/lichkham/{id}/reject")
    public ResponseEntity<?> rejectLichKham(@PathVariable("id") int id) {
        Optional<LichKham> optional = lichkhamRepository.findById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setIsAccept(false);

            emailService.sendSimpleEmail(
                    lichkham.getBenhNhanId().getEmail(),
                    "Lịch khám bị từ chối",
                    "Rất tiếc, lịch khám của bạn vào ngày " + lichkham.getNgay() + " đã bị từ chối."
            );
            return ResponseEntity.ok(lichkhamRepository.save(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    // 4. Lấy lịch trình bác sĩ (đã accept, chưa khám)
    @GetMapping("/lichkham/accepted")
    public ResponseEntity<?> getAcceptedLichKham(Principal principal) {
        User doctor = getCurrentDoctor(principal);
        List<LichKham> lichkhams = lichkhamRepository.findByBacSiIdAndIsAcceptTrueAndDaKhamFalse(doctor.getId());
        return ResponseEntity.ok(lichkhams);
    }

    // 5. Cập nhật trạng thái khám
    @PutMapping("/lichkham/{id}/update-status")
    public ResponseEntity<?> updateLichKhamStatus(@PathVariable("id") int id, @RequestBody LichKham lichkhamDetails) {
        Optional<LichKham> optional = lichkhamRepository.findById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setDaKham(lichkhamDetails.getDaKham());
            return ResponseEntity.ok(lichkhamRepository.save(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    // 6. Tạo đơn khám
    @PostMapping("/lichkham/{id}/donkham")
    public ResponseEntity<?> createDonKham(@PathVariable("id") int lichKhamId, @RequestBody DonKham donKhamRequest, Principal principal) {
        Optional<LichKham> lichKhamOpt = lichkhamRepository.findById(lichKhamId);
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

        DonKham savedDonKham = donKhamRepository.save(donKhamRequest);

        return ResponseEntity.ok(savedDonKham);
    }

    // 7. Thêm kết quả xét nghiệm
    @PostMapping("/donkham/{id}/xetnghiem")
    public ResponseEntity<?> addXetNghiem(@PathVariable("id") int donKhamId, @RequestBody XetNghiem xetNghiemRequest) {
        Optional<DonKham> donKhamOpt = donKhamRepository.getDonKham(donKhamId);
        if (donKhamOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn khám!");
        }

        DonKham donKham = donKhamOpt.get();
        xetNghiemRequest.setDonKhamId(donKham);

        XetNghiem savedXetNghiem = xetNghiemRepository.save(xetNghiemRequest);

        return ResponseEntity.ok(savedXetNghiem);
    }

    // 8. Tìm bệnh theo từ khóa
    @GetMapping("/benh/search")
    public ResponseEntity<?> searchBenh(@RequestParam("keyword") String keyword) {
        List<Benh> benhList = benhRepository.findByTenBenh(keyword);
        return ResponseEntity.ok(benhList);
    }

    // 9. Gán bệnh vào đơn khám
    @PostMapping("/donkham/{id}/benh/{benhId}")
    public ResponseEntity<?> assignBenhToDonKham(@PathVariable("id") int donKhamId, @PathVariable("benhId") int benhId) {
        Optional<DonKham> donKhamOpt = donKhamRepository.getDonKham(donKhamId);
        Benh benhOpt = benhRepository.getBenhById(benhId);

        if (donKhamOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn khám!");
        }

        if (benhOpt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bệnh!");
        }

        DonKham donKham = donKhamOpt.get();
        donKham.setBenhId(benhOpt);
        donKhamRepository.save(donKham);

        return ResponseEntity.ok(donKham);
    }

    // 10. Lấy bệnh theo đơn khám
    @GetMapping("/donkham/{donKhamId}/benh")
    public ResponseEntity<?> getBenhByDonKham(@PathVariable("donKhamId") Long donKhamId) {
        Optional<Benh> benhOpt = donKhamRepository.getBenhByDonKham(donKhamId);
        if (benhOpt.isPresent()) {
            return ResponseEntity.ok(benhOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy bệnh cho đơn khám id = " + donKhamId);
        }
    }

    @PostMapping("/lichkham/{id}/send-invite")
    public ResponseEntity<?> sendTuvanInvite(@PathVariable("id") int id) {
        Optional<LichKham> lichKham = lichkhamRepository.findById(id);
        String email = lichKham.get().getBenhNhanId().getEmail();  

        String roomId = "tu-van-" + id;
        String link = "https://meet.jit.si/" + roomId;

        emailService.sendInviteEmail(email, link);

        return ResponseEntity.ok("Email mời đã được gửi.");
    }

}
