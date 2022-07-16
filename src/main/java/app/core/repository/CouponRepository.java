package app.core.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import app.core.dao.Coupon;
import app.core.dao.Coupon.Category;
import app.core.exception.CouponAppException;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	boolean existsByTitle(String couponTitle) throws CouponAppException;

	public List<Coupon> findByCompanyIdAndCategoryId(Integer companyId, Category category) throws CouponAppException;

	List<Coupon> findByCustomerIdAndCategoryId(Integer id, Category category) throws CouponAppException;

	List<Coupon> findByCustomerIdAndPriceLessThan(Integer id, double maxPrice) throws CouponAppException;

	public List<Coupon> findByCompanyIdAndPriceLessThan(Integer companyId, double maxPrice) throws CouponAppException;

	public List<Coupon> findByCompanyId(Integer companyId) throws CouponAppException;

	public List<Coupon> findByEndDateAfter(LocalDate currentTime) throws CouponAppException;
}
