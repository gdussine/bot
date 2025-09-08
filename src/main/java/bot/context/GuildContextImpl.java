package bot.context;

import java.util.HashMap;
import java.util.Map;

import io.github.gdussine.bot.api.Bot;
import io.github.gdussine.bot.api.GuildContext;
import io.github.gdussine.bot.api.GuildContextKeyed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class GuildContextImpl implements GuildContext {

    protected Bot bot;
    protected Long guildId;
    protected Map<String, String> context = new HashMap<>();

    public GuildContextImpl(Bot bot, Long guildId) {
        this.bot = bot;
        this.guildId = guildId;
    }

    public Guild getGuild() {
        return bot.getJda().getGuildById(guildId);
    }

    private <T> T get(GuildContextType<T> mapper, GuildContextKeyed key) {
        if (!isDefine(key))
            return null;
        return mapper.getFunction().apply(getGuild(), context.get(key.getContextKey()));
    }

    @Override
    public void put(GuildContextKeyed key, String value) {
        context.put(key.getContextKey(), value);
    }

    @Override
    public void remove(GuildContextKeyed key) {
        context.remove(key.getContextKey());
    }

    @Override
    public VoiceChannel getVoiceChannel(GuildContextKeyed key) {
        return get(GuildContextType.VOICE_CHANNEL, key);
    }

    @Override
    public TextChannel getTextChannel(GuildContextKeyed key) {
        return get(GuildContextType.TEXT_CHANNEL, key);
    }

    @Override
    public Role getRole(GuildContextKeyed key) {
        return get(GuildContextType.ROLE, key);
    }

    @Override
    public Emoji getEmoji(GuildContextKeyed key) {
        return get(GuildContextType.EMOJI, key);
    }

    @Override
    public String getString(GuildContextKeyed key) {
        return get(GuildContextType.STRING, key);
    }

    @Override
    public boolean isDefine(GuildContextKeyed key) {
        return context.get(key.getContextKey()) != null;
    }
}
