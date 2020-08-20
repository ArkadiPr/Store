package com.kostandov.bookstore.controllers;

import com.kostandov.bookstore.beans.ServiceCall;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
@AllArgsConstructor
public class ServiceCallController {

    private ServiceCall serviceCall;

    @GetMapping
    public String showAllHistory(Model model){
        model.addAttribute("methodCalls",serviceCall.getAllHistory());
        return "call-history";
    }
}
