package bot.core;

public class BotConfiguration {

    private Long ownerId;
    private String discordToken;
    private String databaseUser;
    private String databasePassword;
    private String databaseDriver;
    private String databaseUrl;

    public String getDiscordToken() {
        return discordToken;
    }

    public void setDiscordToken(String discordToken) {
        this.discordToken = discordToken;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

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

    public void setConfiguration(BotConfiguration botConfiguration){
        this.discordToken = botConfiguration.getDiscordToken();
        this.ownerId = botConfiguration.getOwnerId();
        this.databaseUser = botConfiguration.getDatabaseUser();
        this.databasePassword = botConfiguration.getDatabasePassword();
        this.databaseDriver = botConfiguration.getDatabaseDriver();
        this.databaseUrl = botConfiguration.getDatabaseUrl();
    }

}
