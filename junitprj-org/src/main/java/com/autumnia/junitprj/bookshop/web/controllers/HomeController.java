package com.autumnia.junitprj.bookshop.web.controllers;

import com.autumnia.junitprj.bookshop.web.dto.request.BookRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/")
@RestController
public class HomeController {
    @GetMapping("/")
    public String home( BookRequestDto bookRequestDto) {
        log.debug("홈");
        return "홈";
    }
}
