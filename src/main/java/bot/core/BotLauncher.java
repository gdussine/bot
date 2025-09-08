package bot.core;

import io.github.gdussine.bot.api.Bot;

public class BotLauncher {

    private Bot bot;

    public BotLauncher(String name) {
        BotFactory factory = new BotFactory(name);
        this.bot = factory.createBot();
    }

    public Bot getBot() {
        return bot;
    }

    public void start() {
        bot.run();
    }

    public static void main(String[] args) {
    BotLauncher launcher = new BotLauncher("bot");
    launcher.start();

    }

}
