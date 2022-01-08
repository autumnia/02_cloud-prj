package com.autumnia.aaservice.sample.controller;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class PortalController {
	private final Environment env;

	@GetMapping("")
	public String getUsers() {
		log.debug( "server.http2.enabled: {}", env.getProperty("server.http2.enabled"));
		log.debug("홈페이지");
		return "홈페이지";
	}
}
