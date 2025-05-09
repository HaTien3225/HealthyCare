/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.Role;
import com.xhht.repositories.RoleRepository;
import com.xhht.services.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepo;

    @Override
    public List<Role> getAllRole() {
        return this.roleRepo.getAllRole();
    }

    @Override
    public Role getRoleById(int id) {
        return this.roleRepo.getRoleById(id);
    }
    
    
}
