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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    @Autowired
    private SessionFactory sessionFactory;

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
    @Scheduled(cron = "0 0 7 * * ?")
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

    private void sendExpiredAppointmentEmail(String toEmail, String patientName, LocalDate appointmentDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Thông báo lịch khám đã hết hạn");
        message.setText("Chào " + patientName + ",\n\n"
                + "Lịch khám của bạn vào ngày " + appointmentDate + " đã hết hạn và được hệ thống tự động hủy.\n"
                + "Nếu bạn vẫn cần khám, vui lòng đặt lịch mới.\n\n"
                + "Trân trọng,\nHệ thống tư vấn sức khỏe.");
        mailSender.send(message);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredLichKham() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            LocalDate today = LocalDate.now();
            List<LichKham> expiredList = session
                    .createQuery("FROM LichKham l WHERE l.ngay < :today", LichKham.class)
                    .setParameter("today", today)
                    .getResultList();

            for (LichKham lk : expiredList) {
                String toEmail = lk.getBenhNhanId().getEmail();
                String patientName = lk.getBenhNhanId().getUsername();
                LocalDate appointmentDate = lk.getNgay();

                sendExpiredAppointmentEmail(toEmail, patientName, appointmentDate);

                session.delete(lk); // XÓA bằng chính session đang dùng
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



}
