package bot.context;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import bot.core.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class GuildContextDeserializer extends StdDeserializer<GuildContext> {

    private Bot bot;

    public GuildContextDeserializer(Bot bot) {
        this(null, bot);
    }

    public GuildContextDeserializer(Class<?> vc, Bot bot) {
        super(vc);
        this.bot = bot;
    }

    @Override
    public GuildContext deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JacksonException {
        GuildContext result = new GuildContext();
        JsonNode node = jp.getCodec().readTree(jp);
        Guild guild = bot.getJDA().getGuildById(node.get("guild").numberValue().longValue());
        result.setGuild(guild);
        node.get("emojis").fields()
                .forEachRemaining(x -> result.getEmojis().put(x.getKey(), guild.getEmojiById(x.getValue().asLong())));
        node.get("roles").fields()
                .forEachRemaining(x -> result.getRoles().put(x.getKey(), guild.getRoleById(x.getValue().asLong())));
        node.get("textChannels").fields()
                .forEachRemaining(x -> result.getTextChannels().put(x.getKey(), guild.getTextChannelById(x.getValue().asLong())));
        node.get("voiceChannels").fields()
                .forEachRemaining(x -> result.getVoiceChannels().put(x.getKey(),guild.getVoiceChannelById(x.getValue().asLong())));
        return result;

    }

}
