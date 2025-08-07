package bot.api;

import net.dv8tion.jda.api.hooks.EventListener;

public interface BotListener extends EventListener{

    public Bot getBot();

    public void setBot(Bot bot);
}
