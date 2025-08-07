package bot.context;

import java.util.function.BiFunction;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public enum GuildContextType {

    VOICE_CHANNEL(VoiceChannel.class, (g, id) -> g.getVoiceChannelById(id)),
    TEXT_CHANNEL(TextChannel.class, (g, id) -> g.getTextChannelById(id)),
    ROLE(Role.class, (g, id) -> g.getRoleById(id)),
    EMOJI(Emoji.class, (g, id) -> g.getEmojiById(id)),
    STRING(String.class, (g, id) -> id);

    private BiFunction<Guild, String, ?> mapper;
    private Class<?> type;

    private <T> GuildContextType(Class<T> type, BiFunction<Guild, String, T> mapper) {
        this.type = type;
        this.mapper = mapper;
    }
    
    public BiFunction<Guild, String, ?> getMapper() {
        return mapper;
    }

    public Class<?> getType() {
        return type;
    }

}
