package bot.context.impl;

import io.github.gdussine.bot.api.GuildContextKeyed;

public enum DefaultGuildContextKeys implements GuildContextKeyed {

    LICENSE_KEY("default.license.key"),
    LOGGER_CHANNEL("default.logger.channel");

    private String key;

    private DefaultGuildContextKeys(String key) {
        this.key = key;
    }

    @Override
    public String getContextKey() {
        return key;
    }
}
