package bot.context;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class GuildContextType<T> {
    public static GuildContextType<VoiceChannel> VOICE_CHANNEL = new GuildContextType<>(VoiceChannel.class,
            (guild, id) -> guild.getVoiceChannelById(id), (conf) -> conf.getVoiceChannels());
    public static GuildContextType<TextChannel> TEXT_CHANNEL = new GuildContextType<>(TextChannel.class,
            (guild, id) -> guild.getTextChannelById(id), conf -> conf.getTextChannels());
    public static GuildContextType<Role> ROLE = new GuildContextType<>(Role.class,
            (guild, id) -> guild.getRoleById(id), conf -> conf.getRoles());
    public static GuildContextType<Emoji> EMOJI = new GuildContextType<>(Emoji.class,
            (guild, id) -> guild.getEmojiById(id), conf -> conf.getRoles());

    private Class<T> type;
    private BiFunction<Guild, Long, T> converter;
    private Function<GuildConfiguration, Map<String, Long>> mapper;

    public GuildContextType(
            Class<T> type,
            BiFunction<Guild, Long, T> converter,
            Function<GuildConfiguration, Map<String, Long>> mapper) {
        this.type = type;
        this.converter = converter;
        this.mapper = mapper;

    }

    public T convert(Guild guild, Long id) {
        return converter.apply(guild, id);
    }

    public Map<String, Long> map(GuildConfiguration configuration) {
        return mapper.apply(configuration);
    }

    public Class<T> getType() {
        return type;
    }

    public static GuildContextType<?> byName(String name) {
        if (name.equals(VOICE_CHANNEL.getType().getSimpleName()))
            return VOICE_CHANNEL;
        if (name.equals(TEXT_CHANNEL.getType().getSimpleName()))
            return TEXT_CHANNEL;
        if (name.equals(ROLE.getType().getSimpleName()))
            return ROLE;
        if (name.equals(EMOJI.getType().getSimpleName()))
            return EMOJI;
        return null;
    }
}
