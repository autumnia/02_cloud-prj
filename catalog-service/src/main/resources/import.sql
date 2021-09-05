DROP TABLE IF EXISTS catalogs;
CREATE TABLE IF NOT EXISTS catalogs ( 
	id bigint NOT NULL AUTO_INCREMENT, 
	product_id varchar(120) NOT NULL,
	product_name varchar NOT NULL,
	stock int NOT NULL,
	unit_price int NOT NULL,
	created_At DATE NOT NULL DEFAULT SYSDATE,	
	PRIMARY KEY (id) 
);


insert into catalogs(product_id, product_name, stock, unit_price ) values('CATALOG-001', '사과', 100, 1500);
insert into catalogs(product_id, product_name, stock, unit_price ) values('CATALOG-002', '배', 110, 2000);
insert into catalogs(product_id, product_name, stock, unit_price ) values('CATALOG-003', '바나나', 120, 2500);
