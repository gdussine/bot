package bot;

import bot.context.GuildContext;
import bot.context.GuildContextIndex;
import bot.core.Bot;
import bot.core.BotFactory;

public class Launcher {

    private Bot bot;

    public Launcher() {
        BotFactory factory = new BotFactory("secret/auth.json");
        this.bot = factory.createBot();
    }

    public Bot getBot() {
        return bot;
    }

    public void start() {
        bot.login();
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.start();
        GuildContext ctxt = launcher.getBot().getContext(667885057133772810L);
        System.out.println(ctxt.getRole(GuildContextIndex.ROLE_MEMBER.name()));
    }
}
