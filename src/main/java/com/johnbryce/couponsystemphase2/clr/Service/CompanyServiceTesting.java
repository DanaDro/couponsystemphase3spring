package com.johnbryce.couponsystemphase2.clr.Service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.johnbryce.couponsystemphase2.Bean.Category;
import com.johnbryce.couponsystemphase2.Bean.Company;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Exception.AlreadyExistException;
import com.johnbryce.couponsystemphase2.Exception.CanNotChangeExeption;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Repo.CompanyRepository;
import com.johnbryce.couponsystemphase2.Service.CompanyService;
import com.johnbryce.couponsystemphase2.Utils.Headers;
import com.johnbryce.couponsystemphase2.Utils.Ido;

@Component
@Order(1)
public class CompanyServiceTesting implements CommandLineRunner {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyRepository companyRepo;

	@Override
	public void run(String... args) throws Exception {

		Headers.CompanyServiceTesting();

		Company c1 = new Company("Sahut", "sahut5@gmail.com", "1111");
		Company c2 = new Company("Fitness", "fit5@gmail.com", "555");
		Company c3 = new Company("Bareket", "bareket@gmail.com", "bareket1234");
		Company c4 = new Company("JB", "JB@gmail.com", "Jbbb6460");

		Coupon coupon1 = new Coupon(1, Category.VACATION, "The best Vaction", "Vaction in resort",
				Date.valueOf("2020-08-30"), Date.valueOf("2021-09-08"), 1000, 5555.9, ""); // expired date - for
																									// testing
		Coupon coupon2 = new Coupon(1, Category.FOOD, "In n Out", "best burger in California",
				Date.valueOf("2020-09-20"), Date.valueOf("2021-09-20"), 1000, 50.5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdBVVDv8ySZrQRQQtJ8GrJBSOKA8DjYw_LVg&usqp=CAU");
		Coupon coupon3 = new Coupon(1, Category.ELECTRICITY, "Best Buy", "best computer", Date.valueOf("2020-08-30"),
				Date.valueOf("2022-09-30"), 100, 4000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTC6H3Z_MACn4EdAC36J9bpTdgHecQ-gvQB4w&usqp=CAU");
		Coupon coupon4 = new Coupon(1, Category.FOOD, "Segev Restaurant", "Italian food", Date.valueOf("2020-09-09"),
				Date.valueOf("2022-09-30"), 1550, 300, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcj3GbZK67yklG10NiDZ2O47phndSdz4VkBA&usqp=CAU"); // amount - 0 for testing
		Coupon coupon5 = new Coupon(1, Category.GAMES, "Snake", "90s game", Date.valueOf("2020-09-01"),
				Date.valueOf("2021-09-30"), 500, 10.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnN9mbKosuk3m7eAIjZzRCi0hPc54FkwwhfQ&usqp=CAU");
		Coupon coupon6 = new Coupon(1, Category.CLOTH, "Adict", "Fasion Cloth", Date.valueOf("2020-08-01"),
				Date.valueOf("2021-08-30"), 1000, 300, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpKIl71oiw6WiWR7AfEQdWaVoSksYoSPfNzQ&usqp=CAU");
		Coupon coupon7 = new Coupon(1, Category.RESTAURANT, "Chin Chin", "Asian food for the family",
				Date.valueOf("2020-12-03"), Date.valueOf("2021-03-21"), 200, 200, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6CX4sbr_oeoS4vTxGRs9VP3hjw1PB76Qu3w&usqp=CAU");
		Coupon coupon8 = new Coupon(1, Category.ELECTRICITY, "Ivory", "The smartest phone", Date.valueOf("2020-08-30"), Date.valueOf("2021-09-30"), 100, 4500, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3hfeq5c2lSBNOlI2Ssc0BMlxW2mgCAulNS2ffsIu52v1qi0Ci0NT1Ci_MvRw34B5EiQdNpNGS&usqp=CAc");

		companyRepo.save(c1);
		companyRepo.save(c2);
		companyRepo.save(c3);
		companyRepo.save(c4);
		

		System.out.println("----------------bad logging...----------------");
		try {
			System.out.println(companyService.login("aaa@gmail.com", "aaa"));
		} catch (NotExistException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("----------------good logging...----------------");
		try {
			System.out.println(companyService.login("sahut5@gmail.com", "1111"));
		} catch (NotExistException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("----------------add coupon works----------------");
		try {
			companyService.addCoupon(coupon1);
			companyService.addCoupon(coupon2);
			companyService.addCoupon(coupon3);
			companyService.addCoupon(coupon4);
			companyService.addCoupon(coupon5);
			companyService.addCoupon(coupon6);
			companyService.addCoupon(coupon7);
			companyService.addCoupon(coupon8);
			companyService.addCoupon(new Coupon(1, Category.FOOD, "Frozen Fruits", "fruits", Date.valueOf("2020-08-30"),
					Date.valueOf("2021-08-30"), 200, 41.9, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhuuJmKQ2fd_vu_ZyzDfh7MmRZ6gznPxLnSw&usqp=CAU"));
		} catch (AlreadyExistException e2) {
			System.out.println(e2.getMessage());
		}
		Ido.print(companyService.getCompanyCoupons());
		System.out.println("----------------add coupon with existing title should not work----------------");
		try {
			companyService.addCoupon(new Coupon(1, Category.FOOD, "The best Vaction", "Caribbean Cruz",
					Date.valueOf("2020-08-30"), Date.valueOf("2021-08-30"), 100, 5555.9, "stam"));
		} catch (AlreadyExistException e2) {
			System.out.println(e2.getMessage());
		}
//		System.out.println("----------------update coupon id should not work----------------");
//		try {
//			coupon1.setId(5);
//			companyService.updateCoupon(coupon1);
//		} catch (CanNotChangeExeption e1) {
//			System.out.println(e1.getMessage());
//		}
//		System.out.println("----------------update company id should not work----------------");
//		try {
//			coupon1.setCompanyId(2);
//			companyService.updateCoupon(coupon1);
//		} catch (CanNotChangeExeption e1) {
//			System.out.println(e1.getMessage());
//		}

		System.out.println("----------------update coupon works----------------");
		try {
			coupon1.setAmount(3000);
			companyService.updateCoupon(coupon1);
		} catch (CanNotChangeExeption e1) {
			System.out.println(e1.getMessage());
		}
		Ido.print(companyService.getCompanyCoupons());
		System.out.println(
				"----------------delete coupon should delete customer vs coupons related coupons----------------");
		companyService.deleteCoupon(1);
		System.out.println("----------------get all coupons----------------");
		Ido.print(companyService.getCompanyCoupons());
		System.out.println("----------------get all coupons by category----------------");
		Ido.print(companyService.getCompanyCouponsByCategory(Category.FOOD));
		System.out.println("----------------get all coupons by max price----------------");
		Ido.print(companyService.getCompanyCouponsByPrice(100.0));
		System.out.println("----------------get company's detalis----------------");
		Ido.print(companyService.getCompanyDetails());

	}
}