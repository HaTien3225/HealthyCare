/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class MailSenderServiceImpl implements MailSenderService{

    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gomeow78@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

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
