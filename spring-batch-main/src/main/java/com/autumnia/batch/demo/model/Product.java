package com.autumnia.batch.demo.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name="product")
@Data
public class Product {
	private int productID;
	private String productName;
	private String productDesc;
	private float price;
	private int unit;
}
