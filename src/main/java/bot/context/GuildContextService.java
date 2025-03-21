package bot.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bot.service.core.BotService;
import bot.service.core.GenericBotService;
import net.dv8tion.jda.api.entities.Guild;

@BotService
public class GuildContextService extends GenericBotService implements GuildContextProvider {

    private String filename = "config.json";
    private TypeReference<Map<String, GuildContext>> type = new TypeReference<>() {
    };
    Map<String, GuildContext> contexts;
    private ObjectMapper mapper;

    public Map<String, GuildContext> getContexts() {
        if (contexts == null) {
            SimpleModule mapperModule = new SimpleModule().addDeserializer(Guild.class,
                    new GuildDeserializer(() -> this.getBot()));
            this.mapper = new ObjectMapper().registerModule(mapperModule);
            try (InputStream in = GuildContextService.class.getClassLoader().getResourceAsStream(filename)) {
                contexts = this.mapper.readValue(in, type);
                this.log.info("GuildContext Initialization : {} guild loaded", contexts.size());
            } catch (IOException e) {
                this.log.error("GuildContext Initialization error : {}", e.getMessage());
            }
        }
        return contexts;
    }

    public GuildContext getContext(long guildId) {
        GuildContext context = getContexts().get(Long.toString(guildId));
        if (context == null)
            this.log.warn("Guild:{} has no context configured", guildId);
        return context;
    }

    public GuildContext getContext(Guild guild) {
        GuildContext context = getContexts().get(guild.getId());
        if (context == null)
            this.log.warn("Guild '{}' has no context configured", guild.getName());
        return context;
    }

}
