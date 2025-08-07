package bot.api;

import org.slf4j.Logger;

import bot.service.BotServiceHandler;

public interface BotService {

    public BotServiceHandler getHandler();

    public Logger getLogger();

    public Bot getBot();

    public void setBot(Bot bot);

    public void start();

    public void stop();

    public default String getName() {
        return this.getClass().getSimpleName();
    }

}
