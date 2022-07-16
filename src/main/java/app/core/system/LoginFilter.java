package app.core.system;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

public class LoginFilter implements Filter {

	private SessionManagaer sessionManagaer;

	public LoginFilter(SessionManagaer sessionManagaer) {
		super();
		this.sessionManagaer = sessionManagaer;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String token = servletRequest.getHeader("token");
		if (token != null && sessionManagaer.getSessionExsists(token) != null) {
			chain.doFilter(request, response);
			System.out.println("We have session");
			return;
		}
		if (servletRequest.getMethod().equals("OPTIONS")) {
			chain.doFilter(servletRequest, response);
			return;
		}

		HttpServletResponse servletResponse = (HttpServletResponse) response;
		servletResponse.setHeader("Access-Control-Allow-Origin", "*");
		servletResponse.setHeader("Access-Control-Allow-Headers", "*");
		servletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in");

	}

}
