package bot.context;

import java.util.HashMap;
import java.util.Map;

import bot.core.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class GuildContext {

    protected Bot bot;
    private Map<String, String> configuration = new HashMap<>();
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

    public Map<String, String> getConfiguration() {
		return configuration;
	}

    public Role getRole(String key) {
    	return getGuild().getRoleById(configuration.get(key));
    }

    public TextChannel getTextChannel(String key) {
    	return getGuild().getTextChannelById(configuration.get(key));
    }

    public VoiceChannel getVoiceChannel(String key) {
    	return getGuild().getVoiceChannelById(configuration.get(key));
    }

    public Emoji getEmoji(String key) {
    	return getGuild().getEmojiById(configuration.get(key));
    }
    
    public String getString(String key) {
    	return configuration.get(key);
    }
    
    public void put(GuildContextEntry entry) {
    	configuration.put(entry.getContextKey(), entry.getContextValue());
    }

}
