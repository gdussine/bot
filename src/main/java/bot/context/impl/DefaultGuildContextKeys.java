package bot.context.impl;

import bot.context.GuildContextKey;
import bot.context.GuildContextKeyed;
import bot.context.GuildContextType;

public enum DefaultGuildContextKeys implements GuildContextKeyed {


    LICENSE_KEY(GuildContextType.STRING, "default.license.key"),
    LOGGER_CHANNEL(GuildContextType.TEXT_CHANNEL, "default.logger.channel")
    ;

    private GuildContextKey key;

    private DefaultGuildContextKeys(GuildContextType type, String name) {
        this.key = new GuildContextKey(type, name);
    } 

    @Override
    public GuildContextKey getKey() {
        return key;
    }
}
