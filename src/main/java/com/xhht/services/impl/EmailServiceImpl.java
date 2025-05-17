/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author hatie
 */
@Service
public class EmailServiceImpl implements EmailService{

@Autowired
private JavaMailSender mailSender;

    /**
     * Gửi email đơn giản (text).
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
}
