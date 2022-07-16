package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.dao.Coupon;
import app.core.dao.Coupon.Category;
import app.core.exception.CouponAppException;
import app.core.service.AdminService;
import app.core.service.CustomerService;
import app.core.system.Session;
import app.core.system.SessionManagaer;

@RequestMapping("/customer")
@RestController
@CrossOrigin
public class CustomerController {

	@Autowired
	private SessionManagaer sessionManagaer;
	
	@Autowired
	private CustomerService customer;
	
	@PostMapping("/addCoupon")
	public ResponseEntity<?> buyACoupon(@RequestParam Integer couonId, @RequestHeader String token) throws CouponAppException{
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			customer = (CustomerService) session.getInformations("service");
			Coupon coupon = customer.customerCouponPurchase(couonId);
			System.out.println(coupon);
			return ResponseEntity.status(HttpStatus.OK).body(coupon);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	@GetMapping("/imfo")
	public ResponseEntity<?> getProfileInformation(@RequestHeader String token) throws CouponAppException{
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			customer = (CustomerService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer.getCustomerDetails());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	@GetMapping("/coupons")
	public ResponseEntity<?> customerCoupons(@RequestHeader String token) throws CouponAppException{
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			customer = (CustomerService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer.getAllCostomerCoupons());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@GetMapping("/coupons/category")
	public ResponseEntity<?> customerCouponsByCategory(@RequestParam  String category, @RequestHeader String token) throws CouponAppException{
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			customer = (CustomerService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer.getAllCustomerCouponsByCategory(Category.valueOf(category.toUpperCase())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@GetMapping("/coupons/price")
	public ResponseEntity<?> customerCouponsByPrice(@RequestParam  double price, @RequestHeader String token) throws CouponAppException{
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			customer = (CustomerService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer.getAllCustomerCouponsByMaxPrice(price));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@GetMapping("/coupons/system")
	public ResponseEntity<?> getAllCouponsSystem(@RequestHeader String token) throws CouponAppException{
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			customer = (CustomerService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer.getAllCoupons());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
}
