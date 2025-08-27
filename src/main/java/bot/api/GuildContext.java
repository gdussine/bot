package bot.api;

import bot.context.GuildContextKeyed;
import bot.context.GuildContextValue;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public interface GuildContext {

    public VoiceChannel getVoiceChannel(GuildContextKeyed key);

    public TextChannel getTextChannel(GuildContextKeyed key);

    public GuildContextValue getValue(GuildContextKeyed key);

    public Role getRole(GuildContextKeyed key);

    public Emoji getEmoji(GuildContextKeyed key);

    public String getString(GuildContextKeyed key);

    public String getAsMention(GuildContextKeyed key);

    public void put(GuildContextValue value);

    public void remove(GuildContextKeyed key);

    public boolean isDefine(GuildContextKeyed key);

}
