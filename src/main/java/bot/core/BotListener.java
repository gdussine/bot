package bot.core;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

    BotService service;

    public BotService getService() {
        return service;
    }

    public void setService(BotService service) {
        this.service = service;
    }

    

}
