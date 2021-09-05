package com.autumnia.catalogservice.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autumnia.catalogservice.repository.CatalogEntity;
import com.autumnia.catalogservice.service.CatalogService;
import com.autumnia.catalogservice.vo.CatalogResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
	final CatalogService catalogService;
	final Environment env;
	
	@Autowired
	public CatalogController(CatalogService catalogService, Environment env) {
		this.catalogService = catalogService;
		this.env = env;
	}


	@GetMapping("/catalogs")
	public ResponseEntity<List<CatalogResponse>> getCatalogs() {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		 Iterable<CatalogEntity> catalogs = this.catalogService.getAllCatalogs();
		
		 List<CatalogResponse> result = new ArrayList<>();
		 catalogs.forEach( v -> {
			 result.add( mapper.map(v, CatalogResponse.class) );
		 });
	 
		return ResponseEntity.status(HttpStatus.OK).body( result ) ;
	}	
	
	@GetMapping("/status")
	public String status() {
		String message = String.format("%s 정상 작동 중입니다.  port:%s", 
				this.getCurrentTime(),
				this.env.getProperty("local.server.port"));
		log.info(message);
		
		return message;
	}

	public String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String current_time = sdf.format( time.getTime() );
		return current_time;
	}	
	
}
