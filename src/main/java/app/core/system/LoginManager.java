package app.core.system;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import app.core.exception.CouponAppException;
import app.core.service.AdminService;
import app.core.service.Clients;
import app.core.service.CompanyService;
import app.core.service.CustomerService;

@Component
public class LoginManager {

	@Autowired
	private ApplicationContext ctx;

	public Clients login(String email, String password, @RequestParam ClientsType type) throws CouponAppException {
		switch (type) {
		case Admin:
			AdminService adminService = ctx.getBean(AdminService.class);
			if (adminService.logIn(email, password)) {
				return adminService;
			}
		case Company:
			CompanyService companyService = ctx.getBean(CompanyService.class);
			if (companyService.logIn(email, password)) {
				return companyService;
			}
		case Customer:
			CustomerService customerService = ctx.getBean(CustomerService.class);
			if (customerService.logIn(email, password)) {
				return customerService;
			}
		}
		throw new CouponAppException(HttpStatus.UNAUTHORIZED, "The mail or the password not exsists , try agin");
	}
}
