package com.johnbryce.couponsystemphase2.Bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Coupon> coupons = new ArrayList<>();
	
	public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}	

}
