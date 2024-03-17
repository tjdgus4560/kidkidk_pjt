package com.ssafy.kdkd.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class authController {
    @GetMapping("/api/login")
    public String myPage() {

        return "login";
    }
    @GetMapping("/hi")
    public String hi() {

        return "hi";
    }
}
