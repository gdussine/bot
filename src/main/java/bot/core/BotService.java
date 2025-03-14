package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BotService {

    private Bot bot;
    private Logger log;
    private BotListener listener;

    public BotService(){
        this.log = LoggerFactory.getLogger(getClass());

    }

    public BotListener getListener(){
        return listener;
    }

    public Bot getBot() {
        if(bot == null)
            this.log.error("{} is not started", getClass());
        return bot;
    }

    public void connect(Bot bot) {
        this.bot = bot;
    }

    protected void connect(Bot bot, BotListener listener){
        this.bot = bot;
        this.listener = listener;
        listener.setService(this);
        bot.getJdaBuilder().addEventListeners(listener);
    }

}
