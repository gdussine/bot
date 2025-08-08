package bot.context;

import java.util.HashMap;
import java.util.Map;

import bot.api.Bot;
import bot.api.GuildContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class GuildContextImpl implements GuildContext {

    protected Bot bot;
    protected Long guildId;
    protected Map<GuildContextKey, GuildContextValue> context = new HashMap<>();

    public GuildContextImpl(Bot bot, Long guildId) {
        this.bot = bot;
        this.guildId = guildId;
    }

    public Guild getGuild() {
        return bot.getJda().getGuildById(guildId);
    }

    private <T> T get(Class<T> type, GuildContextKeyed key) {
        GuildContextValue value = getValue(key);
        if (value == null)
            return null;
        return type.cast(key.getKey().getContextType().getMapper().apply(getGuild(), value.getContextValue()));
    }


    @Override
    public void put(GuildContextValue value) {
        context.put(value.getContextKey(), value);
    }

    @Override
    public void remove(String keyName) {
        GuildContextKey key = new GuildContextKey(null, keyName);
        context.remove(key);
    }

    @Override
    public void remove(GuildContextKeyed keyed) {
        context.remove(keyed.getKey());
    }

    @Override
    public VoiceChannel getVoiceChannel(GuildContextKeyed keyed) {
        return get(VoiceChannel.class, keyed);
    }

    @Override
    public TextChannel getTextChannel(GuildContextKeyed keyed) {
        return get(TextChannel.class, keyed);
    }

    @Override
    public GuildContextValue getValue(GuildContextKeyed keyed) {
        return context.get(keyed.getKey());
    }

    @Override
    public Role getRole(GuildContextKeyed keyed) {
        return get(Role.class, keyed);
    }

    @Override
    public Emoji getEmoji(GuildContextKeyed keyed) {
        return get(Emoji.class, keyed);
    }

    @Override
    public String getString(GuildContextKeyed keyed) {
        return get(String.class, keyed);
    }
}
