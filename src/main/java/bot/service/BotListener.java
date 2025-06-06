package bot.service;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class BotListener extends ListenerAdapter {

    BotService service;

    public BotService getService() {
        return service;
    }

    public void setService(BotService service) {
        this.service = service;
    }
}
