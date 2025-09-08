package bot.context;

import java.util.function.BiFunction;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class GuildContextType<T> {

    public final static GuildContextType<TextChannel> TEXT_CHANNEL = new GuildContextType<>(
            TextChannel.class, (g, id) -> g.getTextChannelById(id));

    public static GuildContextType<VoiceChannel> VOICE_CHANNEL = new GuildContextType<>(
            VoiceChannel.class, (g, id) -> g.getVoiceChannelById(id));

    public static GuildContextType<Role> ROLE = new GuildContextType<>(
            Role.class, (g, id) -> g.getRoleById(id));

    public static GuildContextType<Emoji> EMOJI = new GuildContextType<>(
            Emoji.class, (g, id) -> g.getEmojiById(id));

    public static GuildContextType<String> STRING = new GuildContextType<>(
            String.class, (g, id) -> id);

    private BiFunction<Guild, String, T> function;

    private Class<T> type;

    public GuildContextType(Class<T> type, BiFunction<Guild, String, T> function) {
        this.function = function;
    }

    public BiFunction<Guild, String, T> getFunction() {
        return function;
    }

    public Class<T> getType() {
        return type;
    }

}
