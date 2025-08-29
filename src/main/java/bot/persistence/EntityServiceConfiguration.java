package bot.persistence;

import bot.platform.PlatformConfiguration;

public class EntityServiceConfiguration implements PlatformConfiguration {

	private String user;
	private String password;
	private String driver;
	private String url;
	private String mode;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public void setDefaultConfiguration() {
		setUser("test");
		setPassword("test");
		setDriver("org.mariadb.jdbc.Driver");
		setUrl("jdbc:mariadb://localhost/test");
		setMode("update");
	}

}
