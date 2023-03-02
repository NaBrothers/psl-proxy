package com.nabrothers.psl.proxy.controller;

import com.nabrothers.psl.proxy.dto.HttpResponse;
import com.nabrothers.psl.proxy.dto.HttpRequest;
import com.nabrothers.psl.proxy.utils.HttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProxyController {
    @GetMapping(value = "/get")
    @ResponseBody
    public HttpResponse get(@RequestParam("url") String url) {
        return HttpUtils.doGet(url);
    }

    @PostMapping(value = "/post")
    @ResponseBody
    public HttpResponse post(@RequestBody HttpRequest request) {
        return HttpUtils.doPost(request.getUrl(), request.getBody(), request.getHeader());
    }
}
