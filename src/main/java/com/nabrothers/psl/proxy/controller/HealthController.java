package com.nabrothers.psl.proxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {
    @GetMapping(value = "/healthCheck")
    @ResponseBody
    public String healthCheck() {
        return "ok";
    }
}
