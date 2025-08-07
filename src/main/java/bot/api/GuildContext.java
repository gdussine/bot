package bot.api;

import javax.management.relation.Role;

import bot.context.GuildContextValue;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public interface GuildContext {

    public VoiceChannel getVoiceChannel(String key);

    public TextChannel getTextChannel(String key);

    public GuildContextValue getValue(String key);

    public Role getRole(String key);

    public Emoji getEmoji(String key);

    public String getString(String key);

    public void put(GuildContextValue value);

    public void remove(String keyName);

}
