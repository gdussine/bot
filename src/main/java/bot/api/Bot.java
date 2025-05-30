package bot.api;

import java.util.Collection;

import bot.context.GuildContext;
import bot.core.BotConfiguration;
import bot.service.BotService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public interface Bot {

    public <T extends BotService> T getService(Class<T> serviceClass);

    public Collection<BotService> getServices();

    public String getName();

    public GuildContext getContext(long guildId);

    public GuildContext getContext(Guild guild);

    public JDA getJda();

    public BotConfiguration getConfiguration();

    public void run();

    public void shutdown();

}
