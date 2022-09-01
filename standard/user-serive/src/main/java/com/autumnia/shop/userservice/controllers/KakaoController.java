package com.autumnia.shop.userservice.controllers;

import com.autumnia.shop.userservice.common.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoProperties properties;

    @GetMapping("/kakao")
    @ResponseBody
    public KakaoProperties test() {
        return properties;
    }
}
