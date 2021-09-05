package com.autumnia.catalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autumnia.catalogservice.repository.CatalogEntity;
import com.autumnia.catalogservice.repository.CatalogRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Data
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {
	
	final CatalogRepository catalogRepository;
	
	@Autowired
	public CatalogServiceImpl(CatalogRepository catalogRepository) {
		this.catalogRepository = catalogRepository;
	}	
	
	@Override
	public Iterable<CatalogEntity> getAllCatalogs() {
		return this.catalogRepository.findAll();
	}





	
}
