package bot.core;

import bot.context.GuildContextProvider;
import bot.service.core.AbstractBotService;
import jakarta.persistence.EntityManager;

public interface BotProxy extends GuildContextProvider {

    public <T extends AbstractBotService> T get(Class<T> serviceClass);

    public EntityManager getEntityManager();
}
