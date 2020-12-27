package com.johnbryce.couponsystemphase2.Service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.johnbryce.couponsystemphase2.Bean.Company;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Bean.Customer;
import com.johnbryce.couponsystemphase2.Exception.AlreadyExistException;
import com.johnbryce.couponsystemphase2.Exception.CanNotChangeExeption;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Exception.OperationNotAllowedExeption;

@Service
@Lazy
public class AdminService extends ClientService {

	private static final String EMAIL = "admin@admin.com";
	private static final String PASSWORD = "adminAdmin111";

	public boolean login(String email, String password) throws NotExistException {
		if (email.equals(EMAIL) && password.equals(PASSWORD)) {
			return true;
		}
		throw new NotExistException("Email or Password");
	}

	public Company addCompany(Company company) throws AlreadyExistException, NotExistException {
		List<Company> companys = companyRepository.findAll();
		for (Company comp : companys) {
			if (comp.getEmail().equals(company.getEmail())) {
				throw new AlreadyExistException("email");
			}
			if (comp.getName().equals(company.getName())) {
				throw new AlreadyExistException("name");
			}
		}
		return companyRepository.save(company);
	}

	public Company updateCompany(Company company) throws NotExistException, CanNotChangeExeption {
		List<Company> companies = companyRepository.findAll();
		for (Company comp : companies) {
			if (comp.getName().equals(company.getName()) && comp.getId() == company.getId()) {
				return companyRepository.saveAndFlush(company);
			}
		}
		throw new CanNotChangeExeption("Company's name or Id");
	}

	
	public void deleteCompany(int companyId) throws CanNotChangeExeption {
		List<Coupon> companyCoupons = companyRepository.getOne(companyId).getCoupons();
		for (int i = 0; i < companyCoupons.size(); i++) {
			deleteCouponForCustomers(companyCoupons.get(i));
		}
		couponRepository.deleteAll(companyCoupons);
		companyRepository.deleteById(companyId);

	}

	public void deleteCouponForCustomers(Coupon coupon) {
		List<Customer> customers = getCustomers();
		for (Customer cust : customers) {
			List<Coupon> customerCoupons = cust.getCoupons();
			for (int i = 0; i < customerCoupons.size(); i++) {
				Coupon coup = customerCoupons.get(i);
				if (coup.getId() == coupon.getId()) {
					customerCoupons.remove(i);
				}
			}
			cust.setCoupons(customerCoupons);
			customerRepository.saveAndFlush(cust);
		}
	}

	public List<Company> getCompanies() {
		List<Company> company = companyRepository.findAll();
		return company;
	}
	
	public Company getOneCompany(int companyId) {
		return companyRepository.getOne(companyId);
	}

	public Customer addCustomer(Customer customer) throws AlreadyExistException, NotExistException {
		List<Customer> customers = getCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail())) {
				throw new AlreadyExistException("email");
			}
		}
		return customerRepository.save(customer);
	}

	public Customer updateCustomer(Customer customer) throws OperationNotAllowedExeption {
		List<Customer> customers = customerRepository.findAll();
		for (Customer cust : customers) {
			if (cust.getId() == customer.getId()) {
				return customerRepository.saveAndFlush(customer);
			}
		}
		throw new OperationNotAllowedExeption("CANT CHANGE CUSTOMER ID");
	}

	public void deleteCustomer(int customerId) {
		customerRepository.deleteById(customerId);
	}

	public List<Customer> getCustomers() {
		return customerRepository.findAll();
//		for (int i = 0; i < customers.size(); i++) {
//			customers.set(i, getOneCustomer(customers.get(i).getId()));
//		}
	}

	public Customer getOneCustomer(int customerId) {
		return customerRepository.getOne(customerId);
	}
	
	
}
