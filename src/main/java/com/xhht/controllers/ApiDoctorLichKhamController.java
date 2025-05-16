package com.xhht.controllers;

import com.xhht.pojo.DonKham;
import com.xhht.pojo.LichKham;
import com.xhht.pojo.XetNghiem;
import com.xhht.repositories.BenhRepository;
import com.xhht.repositories.DonKhamRepository;
import com.xhht.repositories.LichKhamRepository;
import com.xhht.services.EmailService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
    private EmailService emailService;

    // 1. Lấy danh sách lịch khám đang chờ bác sĩ chấp nhận (isAccept = false)
    @GetMapping("/lichkham/pending")
    public ResponseEntity<?> getPendingLichKham(@RequestParam("bacSiId") Long bacSiId) {
        List<LichKham> lichkhams = lichkhamRepository.findByBacSiIdAndIsAcceptFalse(bacSiId);
        return ResponseEntity.ok(lichkhams);
    }

    // 2. Chấp nhận lịch khám
    @PutMapping("/lichkham/{id}/accept")
    public ResponseEntity<?> acceptLichKham(@PathVariable("id") int id) {
        Optional<LichKham> optional = lichkhamRepository.findById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setIsAccept(true);

//            // Gửi mail thông báo
//            emailService.sendSimpleEmail(
//                    lichkham.getBenhNhanId().getEmail(),
//                    "Lịch khám được chấp nhận",
//                    "Bác sĩ đã chấp nhận lịch khám của bạn vào ngày " + lichkham.getNgay()
//            );
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

//            // Gửi mail thông báo từ chối
//            emailService.sendSimpleEmail(
//                    lichkham.getBenhNhanId().getEmail(),
//                    "Lịch khám bị từ chối",
//                    "Rất tiếc, lịch khám của bạn vào ngày " + lichkham.getNgay() + " đã bị từ chối."
//            );
            return ResponseEntity.ok(lichkhamRepository.save(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    // 4. Lấy lịch trình bác sĩ (lịch khám đã được accept, chưa khám)
    @GetMapping("/lichkham/accepted")
    public ResponseEntity<?> getAcceptedLichKham(@RequestParam("bacSiId") Long bacSiId) {
        List<LichKham> lichkhams = lichkhamRepository.findByBacSiIdAndIsAcceptTrueAndDaKhamFalse(bacSiId);
        return ResponseEntity.ok(lichkhams);
    }

    // 5. Cập nhật trạng thái khám (daKham)
    @PutMapping("/lichkham/{id}/update-status")
    public ResponseEntity<?> updateLichKhamStatus(@PathVariable("id") int id, @RequestBody LichKham lichkhamDetails) {
        Optional<LichKham> optional = lichkhamRepository.findById(id);
        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setDaKham(lichkhamDetails.getDaKham());
            // Bạn có thể cập nhật thêm các trường khác nếu cần
            return ResponseEntity.ok(lichkhamRepository.save(lichkham));
        }
        return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
    }

    @PostMapping("/lichkham/{id}/donkham")
    public ResponseEntity<?> createDonKham(@PathVariable("id") int lichKhamId, @RequestBody DonKham donKhamRequest) {
        Optional<LichKham> lichKhamOpt = lichkhamRepository.findById(lichKhamId);

        if (lichKhamOpt.isPresent()) {
            LichKham lichKham = lichKhamOpt.get();

            if (!lichKham.getIsAccept()) {
                return ResponseEntity.badRequest().body("Lịch khám chưa được bác sĩ duyệt!");
            }

            donKhamRequest.setLichKhamId(lichKham);
            donKhamRequest.setCreatedDate(LocalDate.now());
            DonKham savedDonKham = donKhamRepository.save(donKhamRequest);

            return ResponseEntity.ok(savedDonKham);
        }

        return ResponseEntity.status(404).body("Không tìm thấy lịch khám!");
    }

    @PostMapping("/donkham/{id}/benh")
    public ResponseEntity<?> addBenh(@PathVariable("id") int donKhamId, @RequestBody Benh benhRequest) {
        Optional<DonKham> donKhamOpt = donKhamRepository.findById(donKhamId);

        if (donKhamOpt.isPresent()) {
            benhRequest.setDonKhamId(donKhamOpt.get());
            return ResponseEntity.ok(benhRepository.save(benhRequest));
        }

        return ResponseEntity.status(404).body("Không tìm thấy đơn khám!");
    }

   

    @PostMapping("/donkham/{id}/xetnghiem")
    public ResponseEntity<?> addXetNghiem(@PathVariable("id") int donKhamId, @RequestBody XetNghiem xetNghiem) {
        Optional<DonKham> donKhamOpt = donKhamRepository.findById(donKhamId);

        if (donKhamOpt.isPresent()) {
            xetNghiem.setDonKhamId(donKhamOpt.get());
            return ResponseEntity.ok(xetNghiemRepository.save(xetNghiem));
        }

        return ResponseEntity.status(404).body("Không tìm thấy đơn khám!");
    }

}
