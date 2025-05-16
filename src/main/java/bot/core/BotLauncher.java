package bot.core;

public class BotLauncher {

    private Bot bot;

    public BotLauncher(String name){
        BotFactory factory = new BotFactory(name);
        this.bot = factory.createBot();
    }

    public Bot getBot() {
        return bot;
    }


    public void start() {
        bot.login();
    }

    public static void main(String[] args) {
        BotLauncher launcher = new BotLauncher("bot");
        launcher.start();

    }
}
