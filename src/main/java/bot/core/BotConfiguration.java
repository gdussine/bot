package bot.core;

import bot.platform.PlatformConfiguration;

public class BotConfiguration implements PlatformConfiguration {

    private Long ownerId;
    private String discordToken;

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


    @Override
    public void setDefaultConfiguration() {
        setDiscordToken("TOKEN");
        setOwnerId(0L);
    }

}
