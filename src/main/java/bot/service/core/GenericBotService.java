package bot.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.core.Bot;

public abstract class GenericBotService {

    private Bot bot;
    protected Logger log;
    private BotListener listener;

    public GenericBotService() {
        this.log = LoggerFactory.getLogger(getClass());
    }

    public BotListener getListener() {
        return listener;
    }

    public Bot getBot() {
        if (bot == null)
            this.log.error("{} is not started", getClass());
        return bot;
    }

    public void setListener(Class<? extends BotListener> listenerClass) {
        try {
            BotListener listener = listenerClass.getConstructor().newInstance();
            listener.setService(this);
            this.listener = listener;
        } catch (InstantiationException e) {
            this.listener = null;
        } catch (Exception e) {
            this.log.error("BotListener {} is not set in BotService {} : {}", listenerClass.getSimpleName(),
                    this.getClass().getSimpleName(), e.getMessage());
        }
    }

    public void connect(Bot bot) {
        this.bot = bot;
        if (listener != null)
            bot.getJdaBuilder().addEventListeners(listener);
    }

}
