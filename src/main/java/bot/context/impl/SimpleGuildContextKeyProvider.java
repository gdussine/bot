package bot.context.impl;

import java.util.Arrays;
import java.util.List;

import bot.context.GuildContextKey;
import bot.context.GuildContextKeyProvider;
import bot.context.GuildContextKeyed;

public class SimpleGuildContextKeyProvider implements GuildContextKeyProvider {

    @Override
    public List<GuildContextKey> provide() {
        return this.provide(DefaultGuildContextKeys.class);
    }

    public <E extends Enum<E> & GuildContextKeyed> List<GuildContextKey> provide(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).map(e -> e.getKey()).toList();
    }

}
