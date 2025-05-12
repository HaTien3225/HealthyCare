/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.TinNhan;
import java.util.List;

/**
 *
 * @author hatie
 */
public interface TinNhanRepository {
    List<TinNhan> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    TinNhan save(TinNhan tinnhan);
}
