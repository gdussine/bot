package bot.core;

import io.github.gdussine.bot.api.BotConfiguration;

public class DiscordConfiguration implements BotConfiguration {

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
    public String exportJSON() {
        throw new UnsupportedOperationException("Unimplemented method 'exportJSON'");
    }

    @Override
    public void importJSON(String arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'importJSON'");
    }

    @Override
    public void initialize() {
        setDiscordToken("TOKEN");
        setOwnerId(0L);
    }

}
