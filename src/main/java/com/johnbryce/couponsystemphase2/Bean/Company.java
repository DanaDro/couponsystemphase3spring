package com.johnbryce.couponsystemphase2.Bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "companies")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}, orphanRemoval = true)
	private List<Coupon> coupons;
	
	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		
	}

}
