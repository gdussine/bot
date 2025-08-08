package bot.context.impl;

import bot.context.GuildContextKey;
import bot.context.GuildContextKeyed;
import bot.context.GuildContextType;

public enum DefaultGuildContextKeys implements GuildContextKeyed {


    LICENSE_VALUE(new GuildContextKey(GuildContextType.STRING, "default.license.value"));

    private GuildContextKey key;

    private DefaultGuildContextKeys(GuildContextKey key) {
        this.key = key;
    } 

    @Override
    public GuildContextKey getKey() {
        return key;
    }
}
