package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.core.dao.User;
import app.core.exception.CouponAppException;
import app.core.service.Clients;
import app.core.system.ClientsType;
import app.core.system.LoginManager;
import app.core.system.Session;
import app.core.system.SessionManagaer;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private SessionManagaer sessionManagaer;
	
	private Session session;

	@PostMapping("/login")
	public String login(@RequestBody User user) {
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb : " + user);
		try {
			Clients service = loginManager.login(user.getEmail(), user.getPassword(), ClientsType.valueOf(user.getClientType()));
			if(service!= null) {
				session = sessionManagaer.creatSession();
				session.setInformations("service", service);
				return "{\"token\": \"" + session.token + "\"}";
			}
		} catch (Exception e) {
			throw new CouponAppException(HttpStatus.BAD_REQUEST, "plz try agin", e.getCause());
		}
		throw new CouponAppException(HttpStatus.UNAUTHORIZED, "plz try agin");
	}
}
