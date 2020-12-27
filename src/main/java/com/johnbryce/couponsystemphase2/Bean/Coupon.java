package com.johnbryce.couponsystemphase2.Bean;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "coupons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int companyId;
	private Category category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private double price;
	private String image;

	public Coupon(int companyId, Category category, String title, String description, Date startDate, Date endDate,
			int amount, double price, String image) {
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

}
