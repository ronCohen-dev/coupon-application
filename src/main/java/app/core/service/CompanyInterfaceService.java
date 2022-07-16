package app.core.service;

import java.util.List;
import app.core.dao.Company;
import app.core.dao.Coupon;
import app.core.dao.Coupon.Category;
import app.core.exception.CouponAppException;

public interface CompanyInterfaceService extends Clients {

	boolean logIn(String user, String email) throws CouponAppException;

	Coupon addCouponToCompany(Coupon coupons) throws CouponAppException;

	Coupon updateCurrentCoupon(Coupon coupons, Integer couponId) throws CouponAppException;

	Coupon deleteOneCouponOfCompany(Integer couponId) throws CouponAppException;

	List<Coupon> getAllCouponsOfOneCompany() throws CouponAppException;

	List<Coupon> getCompanyCouponsByCategory(Category category) throws CouponAppException;

	List<Coupon> getAllCompanyCouponsByMaxPrice(double maxPrice) throws CouponAppException;

	Company getCompanyDetails() throws CouponAppException;

}
