package bot.context;

import bot.core.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class GuildContext {

    protected Bot bot;

    protected GuildConfiguration configuration;

    public GuildContext(Bot bot, GuildConfiguration configuration) {
        this.bot = bot;
        this.configuration = configuration;
    }

    protected <T> T getByType(GuildContextType<T> type, String key) {
        Long id = type.map(this.configuration).get(key);
        if (id == null || id == 0)
            return null;
        return type.convert(getGuild(), id);
    }

    public Bot geBot() {
        return bot;
    }

    public GuildConfiguration getConfiguration() {
        return configuration;
    }

    public Guild getGuild() {
        return bot.getJDA().getGuildById(configuration.getId());
    }

    public Role getRole(String key) {
        return getByType(GuildContextType.ROLE, key);
    }

    public TextChannel getTextChannel(String key) {
        return getByType(GuildContextType.TEXT_CHANNEL, key);
    }

    public VoiceChannel getVoiceChannel(String key) {
        return getByType(GuildContextType.VOICE_CHANNEL, key);
    }

    public Emoji getEmoji(String key) {
        return getByType(GuildContextType.EMOJI, key);
    }

}
