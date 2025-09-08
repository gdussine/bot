package bot.apiold.framework;

import io.github.gdussine.bot.api.Bot;
import io.github.gdussine.bot.api.BotListener;
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
