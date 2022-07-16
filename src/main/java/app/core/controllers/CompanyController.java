package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.dao.Coupon;
import app.core.dao.Coupon.Category;
import app.core.exception.CouponAppException;
import app.core.service.CompanyService;
import app.core.system.Session;
import app.core.system.SessionManagaer;

@RequestMapping("/company")
@RestController
@CrossOrigin
public class CompanyController {

	@Autowired
	SessionManagaer sessionManagaer;

	@Autowired
	CompanyService company;

	@PostMapping("/addCoupon")
	public ResponseEntity<?> createACoupon(@RequestBody Coupon coupon, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			company = (CompanyService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.CREATED).body(company.addCouponToCompany(coupon));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateACoupon(@RequestBody Coupon coupon, @RequestParam int couponId,
			@RequestHeader String token) throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			company = (CompanyService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.OK).body(company.updateCurrentCoupon(coupon, couponId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteCoupon(@RequestParam int couponId, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			company = (CompanyService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.OK).body(company.deleteOneCouponOfCompany(couponId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/coupons/company")
	public ResponseEntity<?> companyCoupons(@RequestHeader String token) throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			company = (CompanyService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.OK).body(company.getAllCouponsOfOneCompany());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/coupons/category")
	public ResponseEntity<?> companyCouponsByCategory(@RequestParam String category, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			company = (CompanyService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.OK).body(company.getCompanyCouponsByCategory(Category.valueOf(category.toUpperCase())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/coupons/price")
	public ResponseEntity<?> companyCouponsByPrice(@RequestParam double price, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			company = (CompanyService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.OK).body(company.getAllCompanyCouponsByMaxPrice(price));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/info")
	public ResponseEntity<?> getProfileInformation(@RequestHeader String token) throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			company = (CompanyService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.OK).body(company.getCompanyDetails());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

}
