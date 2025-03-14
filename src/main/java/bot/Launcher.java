package bot;

import bot.core.Bot;
import bot.core.BotBuilder;

public class Launcher {

    public static void main(String[] args){
        System.out.println("Starting!");
        Bot bot = new BotBuilder().build();
        bot.login(args[0]);
    }
    
}
