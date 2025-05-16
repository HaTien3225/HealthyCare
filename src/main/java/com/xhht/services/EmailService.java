/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xhht.services;

/**
 *
 * @author hatie
 */
public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
