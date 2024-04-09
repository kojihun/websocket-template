package com.develop.websocket.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {
    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/{memberId}")
    public String chatRoomPage(@PathVariable(name = "memberId") String memberId, HttpSession session, Model model) {
        model.addAttribute("name", memberId);
        return "chatRoom";
    }
}
