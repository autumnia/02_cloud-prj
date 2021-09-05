package com.autumnia.catalogservice.service;

import com.autumnia.catalogservice.repository.CatalogEntity;

public interface CatalogService {
	Iterable<CatalogEntity> getAllCatalogs();
}
