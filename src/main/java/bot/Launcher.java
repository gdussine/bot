package bot;

import java.io.IOException;
import java.io.InputStream;

import bot.context.GuildContext;
import bot.core.Bot;

public class Launcher {

    public static void main(String[] args){
        System.out.println("Starting!");
        Bot bot = new Bot();
        bot.login(args[0]);
        InputStream in = Launcher.class.getClassLoader().getResourceAsStream("config.json");
        try {
            GuildContext config = bot.getMapper().readValue(in ,GuildContext.class);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bot.logout();
    }
    
}
