package bot.context;

import java.util.HashMap;
import java.util.Map;

import javax.management.relation.Role;

import bot.api.Bot;
import bot.api.GuildContext;
import net.dv8tion.jda.api.entities.Guild;
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

    private Object get(GuildContextType type, String keyName) {
        GuildContextValue value = getValue(keyName);
        if (value == null)
            return null;
        return type.getType().cast(type.getMapper().apply(getGuild(), value.getContextValue()));
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
    public VoiceChannel getVoiceChannel(String keyName) {
        return (VoiceChannel)get(GuildContextType.VOICE_CHANNEL, keyName);
    }

    @Override
    public TextChannel getTextChannel(String keyName) {
        return (TextChannel) get(GuildContextType.TEXT_CHANNEL, keyName);
    }

    @Override
    public GuildContextValue getValue(String keyName) {
        GuildContextKey key = new GuildContextKey(null, keyName);
        GuildContextValue value = context.get(key);
        return value;
    }

    @Override
    public Role getRole(String keyName) {
        return (Role) get(GuildContextType.ROLE, keyName);
    }

    @Override
    public Emoji getEmoji(String keyName) {
        return (Emoji) get(GuildContextType.EMOJI, keyName);
    }

    @Override
    public String getString(String keyName) {
        return (String) get(GuildContextType.STRING, keyName);
    }
}
