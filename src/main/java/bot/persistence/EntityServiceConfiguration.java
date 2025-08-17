package bot.persistence;

import bot.platform.PlatformConfiguration;

public class EntityServiceConfiguration implements PlatformConfiguration {

    private String databaseUser;
    private String databasePassword;
    private String databaseDriver;
    private String databaseUrl;

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    @Override
    public void setDefaultConfiguration() {
        setDatabaseUser("test");
        setDatabasePassword("test");
        setDatabaseDriver("org.mariadb.jdbc.Driver");
        setDatabaseUrl("jdbc:mariadb://localhost/test");
    }

}
