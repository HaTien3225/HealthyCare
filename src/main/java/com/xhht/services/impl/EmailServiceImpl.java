/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.LichKham;
import com.xhht.repositories.LichKhamRepository;
import com.xhht.services.EmailService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hatie
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private LichKhamRepository lichKhamRepository;

    /**
     * Gửi email đơn giản (text).
     *
     * @param to Người nhận
     * @param subject Tiêu đề email
     * @param text Nội dung email
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        // message.setFrom("your_email@gmail.com"); // Có thể set nếu cần

        mailSender.send(message);
    }

    public void sendInviteEmail(String email, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mời tham gia tư vấn trực tuyến");
        message.setText("Chào bạn,\n\nBạn có lịch tư vấn từ xa sắp diễn ra.\nVui lòng truy cập link sau để tham gia tư vấn:\n\n" + link + "\n\nTrân trọng,\nHệ thống tư vấn sức khỏe");
        mailSender.send(message);
    }

//    // Gửi thông báo nhắc nhở vào 8h sáng mỗi ngày
//    @Scheduled(cron = "0 42 10 * * ?")
//    public void checkAppointmentsForTomorrow() {
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//        List<LichKham> appointments = lichKhamRepository.getLichKhamByDate(tomorrow);
//
//        for (LichKham appt : appointments) {
//            sendAppointmentReminder(
//                appt.getBenhNhanId().getEmail(),
//                appt.getBenhNhanId().getUsername(),
//                tomorrow
//            );
//        }
//    }
    @Transactional
    @Scheduled(cron = "0 30 11 * * ?")
    public void checkAppointmentsForTomorrow() {
        System.out.println(">>> [SCHEDULED] Đang kiểm tra lịch khám của ngày mai...");

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<LichKham> appointments = lichKhamRepository.getLichKhamByDate(tomorrow);

        for (LichKham appt : appointments) {
            sendAppointmentReminder(
                    appt.getBenhNhanId().getEmail(),
                    appt.getBenhNhanId().getUsername(),
                    tomorrow
            );
        }
    }

    private void sendAppointmentReminder(String toEmail, String patientName, LocalDate appointmentDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Nhắc nhở lịch khám");
        message.setText("Chào " + patientName + ", bạn có lịch khám vào ngày " + appointmentDate + ". Vui lòng đến đúng giờ.");
        mailSender.send(message);
    }
}
