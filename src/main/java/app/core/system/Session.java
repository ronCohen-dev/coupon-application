package app.core.system;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Session {

	public String token;
	private long lastAccessed;
	@Value("${session.max.inactive.interval:300000}")
	private long maxInactiveInterval;
	private static final int TOKEN_MAX_LENGTH = 15;
	private Map<String, Object> informations = new HashMap<String, Object>();

	{
		token = UUID.randomUUID().toString().replace("-", "").substring(TOKEN_MAX_LENGTH);
		resetLastAccessed();
	}

	public void resetLastAccessed() {
		lastAccessed = System.currentTimeMillis();
	}

	@PostConstruct
	private void formatMaxInactiveInterval() {
		maxInactiveInterval = TimeUnit.MINUTES.toMinutes(maxInactiveInterval);
	}

	public long getLastAccessed() {
		return lastAccessed;
	}

	public long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setInformations(String key,Object value) {
		informations.put(key, value);
	}
	public Object getInformations(String key) {
	return	informations.get(key);
	}

	public void setToken(String token) {
		this.token = token;
	}


		
	
}
