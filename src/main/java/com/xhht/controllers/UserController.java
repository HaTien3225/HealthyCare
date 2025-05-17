/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.DonKham;
import com.xhht.pojo.Role;
import com.xhht.pojo.User;
import com.xhht.services.BenhVienService;
import com.xhht.services.DonKhamService;
import com.xhht.services.EmailService;
import com.xhht.services.GiayPhepHanhNgheService;
import com.xhht.services.KhoaService;
import com.xhht.services.MailSenderService;
import com.xhht.services.RoleService;
import com.xhht.services.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author lehuy
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private BenhVienService benhVienService;

    @Autowired
    private GiayPhepHanhNgheService giayPhepService;

    @Autowired
    private DonKhamService donKhamService;

    @Autowired
    private EmailService mailSenderService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute(value = "user") User user, RedirectAttributes redirectAttributes,
            @RequestParam(name = "roleId", required = true) int roleId,
            @RequestParam(name = "khoaid", required = false) String khoaIdStr
    ) {
        user.setCreatedDate(LocalDate.now());
        user.setRole(this.roleService.getRoleById(roleId));
        if (khoaIdStr != null && !khoaIdStr.isEmpty()) {
            Integer khoaId = Integer.parseInt(khoaIdStr);
            user.setKhoaId(this.khoaService.getKhoaByKhoaId(khoaId));
        }
        try {
            this.userService.createOrUpdate(user);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tạo user thất bại: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/create-form")
    public String createUserForm(Model model) {
        User u = new User();
        u.setIsActive(false);
        model.addAttribute("user", u);
        model.addAttribute("benhviens", this.benhVienService.getBenhViens(null));
        model.addAttribute("roles", this.roleService.getAllRole());
        return "user_create_form";
    }

    @GetMapping("/admin/users")
    public String listUser(Model model, @RequestParam Map<String, String> params) {
        String kw = params.get("kw");
        String page = params.getOrDefault("page", "1");
        List<User> users = this.userService.getAllUser(params);
        List<Role> roles = this.roleService.getAllRole();
        model.addAttribute("kwr", kw);
        model.addAttribute("page", page);
        model.addAttribute("user", users);
        model.addAttribute("roles", roles);
        model.addAttribute("benhviens", this.benhVienService.getBenhViens(null));
        return "user_manage";
    }

    @GetMapping("/admin/users/{id}")
    public String userDetailView(Model model, @PathVariable(value = "id", required = true) int id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("role", user.getRole().getRole());
        model.addAttribute("giayphep", user.getGiayPhepHanhNgheId());
        if (user.getRole().getRole().equals("ROLE_DOCTOR")) {
            model.addAttribute("tenkhoa", user.getKhoaId().getTenKhoa());
            model.addAttribute("tenbenhvien", user.getKhoaId().getBenhvien().getTenBenhVien());
        } else {
            model.addAttribute("tenkhoa", null);
            model.addAttribute("tenbenhvien", null);
        }
        return "user_detail_admin";
    }

    @PostMapping("/admin/users/{userid}")
    public String updateUserStatusandLicenseStatus(@PathVariable(name = "userid", required = true) int userid,
            @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes) {
        try {
            if (this.userService.getUserById(userid).getGiayPhepHanhNgheId() != null) {
                Boolean isValid = false;
                String isValidStr = params.get("isValid");
                if (isValidStr != null && isValidStr.equals("true")) {
                    isValid = true;
                }

                String giayPhepId = params.get("giayphepid");
                giayPhepService.updateGiayPhepStatus(Integer.parseInt(giayPhepId), isValid);
            }

            Boolean isActive = false;
            String isActiveStr = params.get("isActive");
            if (isActiveStr != null && isActiveStr.equals("true")) {
                isActive = true;
            }
            userService.updateUserStatus(userid, isActive);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật user thất bại: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/vnpay-return")
    public String handleVNPayReturn(@RequestParam Map<String, String> params, Model model) {
        // Kiểm tra response code từ VNPay
        String responseCode = params.get("vnp_ResponseCode");

        if ("00".equals(responseCode)) {
            String donKhamId = params.get("vnp_OrderInfo");
            this.donKhamService.updateIsPaid(Integer.parseInt(donKhamId), true);
            Optional<DonKham> dk = donKhamService.getDonKham(Integer.parseInt(donKhamId));
            String mailBody = "Quý khách đã thanh toán thành công đơn khám: id "+String.valueOf(donKhamId)+", với tổng tiền : "+
                    Integer.parseInt(params.get("vnp_Amount"))/100 + " VND, vào ngày : "+LocalDate.now();

            mailSenderService.sendSimpleEmail(dk.get().getHoSoSucKhoeId().getBenhNhanId().getEmail(), "Thông Báo Đã Thanh Toán Đơn Khám", mailBody);
            model.addAttribute("nofi", "THANH TOAN THANH CONG");

        } else {
            model.addAttribute("nofi", "THANH TOAN THAT BAI");
        }
        return "vnpay_return";
    }
}
