package bot.api.simple;

import bot.api.Bot;
import bot.api.BotListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class TemplateBotListener extends ListenerAdapter implements BotListener  {

    protected Bot bot;

    @Override
    public Bot getBot() {
        return bot;
    }

    @Override
    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
