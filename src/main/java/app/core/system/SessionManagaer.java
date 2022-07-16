package app.core.system;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import app.core.exception.CouponAppException;

@Component
public class SessionManagaer {

	@Autowired
	private ApplicationContext context;
	private Map<String, Session> sessionsOfClients = new ConcurrentHashMap<String, Session>();
	private Timer timer = new Timer();


	private boolean isSessionExpired(Session session) {
		return System.currentTimeMillis() - session.getLastAccessed() > session.getMaxInactiveInterval();
	}

	@Scheduled(initialDelayString ="${session.managar.activate.task.time.start}", fixedDelayString = "${session.managar.activate.task.time}")
	private void clennerSessions() {
		for (String sessionToken : sessionsOfClients.keySet()) {
			Session session = sessionsOfClients.get(sessionToken);
			if (isSessionExpired(session)) {
				invaidate(session);
				System.out.println("session experid : " + session);
			}
		}
	}

	@PreDestroy
	private void destroy() {
		timer.cancel();
		System.out.println("timer canseled");
	}

	public Session creatSession() {
		Session session = context.getBean(Session.class);
		sessionsOfClients.put(session.token, session);
		return session;
	}

	public void invaidate(Session session) {
		sessionsOfClients.remove(session.token);
	}

	public Session getSessionExsists(String ssesionToken) {
		Session session = sessionsOfClients.get(ssesionToken);
		if (session != null) {
			if (!isSessionExpired(session)) {
				session.resetLastAccessed();
				return session;
			} else {
				invaidate(session);
				throw new CouponAppException(HttpStatus.GATEWAY_TIMEOUT,
						"You haven't done anything in the last 30 minutes, you have been logged out");
			}
		}
		throw new CouponAppException(HttpStatus.UNAUTHORIZED, "you nedd to log im again");
	}
}
