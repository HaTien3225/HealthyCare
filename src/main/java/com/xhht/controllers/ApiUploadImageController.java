package com.xhht.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")  // Cho phép gọi API từ frontend bất kỳ (tùy chỉnh theo domain thật)
@RequestMapping("/api")
public class ApiUploadImageController {

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/upload-image")
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            return Map.of(
                "secure_url", result.get("secure_url"),
                "public_id", result.get("public_id")
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            return Map.of("error", "Upload thất bại");
        }
    }
}
