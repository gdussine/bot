package bot;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import bot.core.Bot;
import bot.core.BotConfiguration;
import bot.core.BotFactory;
import okio.Path;

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

    public void init(){
        Path.get("~/.bot/config").toFile().mkdirs();
        //new File("~/.bot/out").mkdir();
    }

    public void initAuthFile(){
        BotConfiguration configuration = new BotConfiguration();
        configuration.setDiscordToken("DISCORD_TOKEN");
        configuration.setOwnerId(0L);
        configuration.setDatabaseUser("test");
        configuration.setDatabasePassword("test");
        configuration.setDatabaseDriver("org.mariadb.jdbc.Driver");
        configuration.setDatabaseUrl("jdbc:mariadb://localhost/test");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(authPath), configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        bot.login();
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();        

    }
}
