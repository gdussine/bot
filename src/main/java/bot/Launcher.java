package bot;

import bot.core.Bot;
import bot.core.BotFactory;

public class Launcher {

    public static void main(String[] args){
        BotFactory factory = new BotFactory();
        Bot bot = factory.iniBot();
        bot.login(args[0]);
    }
    
}
