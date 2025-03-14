package bot.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bot.core.BotService;
import net.dv8tion.jda.api.entities.Guild;

public class GuildContextService extends BotService implements GuildContextProvider {

    private String filename = "config.json";
    private TypeReference<Map<String, GuildContext>> type = new TypeReference<>() {
    };
    Map<String, GuildContext> contexts;
    private ObjectMapper mapper;

    public Map<String, GuildContext> getContexts() {
        SimpleModule mapperModule = new SimpleModule().addDeserializer(Guild.class,
                new GuildDeserializer(() -> this.getBot()));
        this.mapper = new ObjectMapper().registerModule(mapperModule);
        if (contexts == null) {
            try (InputStream in = GuildContextService.class.getClassLoader().getResourceAsStream(filename)) {
                contexts = this.mapper.readValue(in, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contexts;
    }

    public GuildContext getContext(long guildId) {
        return getContexts().get(Long.toString(guildId));
    }

    public GuildContext getContext(Guild guild) {
        return getContext(guild.getIdLong());
    }

}
