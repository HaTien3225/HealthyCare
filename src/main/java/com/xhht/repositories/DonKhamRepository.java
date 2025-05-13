/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import java.math.BigDecimal;

/**
 *
 * @author lehuy
 */
public interface DonKhamRepository {
    BigDecimal getTotalRevenue(int month, int year);
}
