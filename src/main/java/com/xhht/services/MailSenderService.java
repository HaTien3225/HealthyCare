/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

/**
 *
 * @author lehuy
 */
public interface MailSenderService {
    void sendEmail(String toEmail, String subject, String body);
}
