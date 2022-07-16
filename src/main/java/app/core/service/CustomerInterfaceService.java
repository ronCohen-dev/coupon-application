package app.core.service;

import java.util.List;


import app.core.dao.Coupon;
import app.core.dao.Customer;
import app.core.dao.Coupon.Category;
import app.core.exception.CouponAppException;

public interface CustomerInterfaceService extends Clients {

	boolean logIn(String email, String password) throws CouponAppException;

	Coupon customerCouponPurchase(Integer couponId) throws CouponAppException;

	Customer getCustomerDetails() throws CouponAppException;

	List<Coupon> getAllCostomerCoupons() throws CouponAppException;

	List<Coupon> getAllCustomerCouponsByCategory(Category category) throws CouponAppException;

	List<Coupon> getAllCustomerCouponsByMaxPrice(double maxPrice) throws CouponAppException;

	List<Coupon> getAllCoupons() throws CouponAppException;
}
