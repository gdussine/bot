package bot.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import bot.core.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class GuildContextFileRepository implements GuildContextSupplier{

    private String filename = "config.json";
    private TypeReference<Map<String, GuildContext>> type = new TypeReference<>() {};
    Map<String, GuildContext> contexts = new HashMap<>();

    public GuildContextFileRepository(Bot bot){
         InputStream in = GuildContextFileRepository.class.getClassLoader().getResourceAsStream(filename);
        try {
            contexts = bot.getMapper().readValue(in ,type);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GuildContext getContext(long guildId) {
        return contexts.get(Long.toString(guildId));
    }

    @Override
    public GuildContext getContext(Guild guild) {
        return getContext(guild.getIdLong());
    }

}
