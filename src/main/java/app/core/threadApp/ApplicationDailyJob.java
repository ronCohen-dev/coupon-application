package app.core.threadApp;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import app.core.dao.Coupon;
import app.core.exception.CouponAppException;
import app.core.repository.CouponRepository;

@Component
@Transactional
public class ApplicationDailyJob {

	@Autowired
	private CouponRepository couponRepository;

	public ApplicationDailyJob() {
		super();
	}

	/**
	 * The method defines thread action to delete expired coupons from database
	 * Current version runs after a day this enum timeUnit
	 */
	
	@Scheduled(initialDelayString =  "${app.core.daily.job.start.clenner}", fixedDelayString = "${app.core.daily.job}")
	public void run() {
			try {
				List<Coupon> coupons = couponRepository.findByEndDateAfter(LocalDate.now());
				System.out.println("**** Expired coupons ****");
				for (Coupon coupon : coupons) {
					System.out.println(coupon);
					couponRepository.delete(coupon);
				}
			} catch (CouponAppException e) {
				e.printStackTrace();
		}
	}

}
