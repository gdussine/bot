package bot.service.core;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

    AbstractBotService service;

    public AbstractBotService getService() {
        return service;
    }

    public void setService(AbstractBotService service) {
        this.service = service;
    }
    

}
