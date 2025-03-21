package bot.service.core;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

    GenericBotService service;

    public GenericBotService getService() {
        return service;
    }

    public void setService(GenericBotService service) {
        this.service = service;
    }
    

}
