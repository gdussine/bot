package bot.context;

import java.util.HashMap;
import java.util.Map;

import bot.api.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class GuildContext {

    protected Bot bot;
    private Map<String, GuildContextEntry> contextMap = new HashMap<>();
    private Long guildId;

    public GuildContext(Bot bot, Long guildId) {
        this.bot = bot;
        this.guildId = guildId;
    }

    public Bot geBot() {
        return bot;
    }

    public Guild getGuild() {
        return bot.getJda().getGuildById(guildId);
    }

    public Role getRole(String key) {
        return getGuild().getRoleById(this.get(key));
    }

    public TextChannel getTextChannel(String key) {
        return getGuild().getTextChannelById(this.get(key));
    }

    public VoiceChannel getVoiceChannel(String key) {
        return getGuild().getVoiceChannelById(this.get(key));
    }

    public Emoji getEmoji(String key) {
        return getGuild().getEmojiById(this.get(key));
    }

    public GuildContextEntry getEntry(String key) {
        return contextMap.get(key);
    }

    public void putEntry(GuildContextEntry entry) {
        contextMap.put(entry.getContextKey(), entry);
    }

    public String get(String key) {
        GuildContextEntry entry = getEntry(key);
        if (entry == null)
            return null;
        return entry.getContextValue();
    }

    public Map<String, GuildContextEntry> getContextMap() {
        return contextMap;
    }

    public void remove(String key) {
        contextMap.remove(key);
    }

}
