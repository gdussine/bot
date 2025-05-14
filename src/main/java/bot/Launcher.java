package bot;

import bot.core.Bot;
import bot.core.BotFactory;

public class Launcher {

    private Bot bot;
    private String authPath;

    public Launcher(){
        BotFactory factory = new BotFactory("bot");
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

    }
}
