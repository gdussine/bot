package bot.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import bot.platform.PlatformService;
import bot.service.core.BotServiceFactory;
import net.dv8tion.jda.api.JDABuilder;

public class BotFactory {

    protected Logger log;
    protected String authPath;
    private ObjectMapper mapper;
    private String name;

    protected BotServiceFactory botServiceFactory;
    protected BotConfiguration botConfiguration;
    protected Bot bot;

    public BotFactory(String name) {
        this.bot = new Bot();
        this.botServiceFactory = new BotServiceFactory(bot);
        this.name = name;
        this.log = LoggerFactory.getLogger(getClass());
        this.mapper = new ObjectMapper();
    }

    public Bot createBot() {
        this.initBot();
        this.initServices();
        this.initConfiguration();
        this.provideConfiguration(bot);
        this.provideServices(bot);
        return bot;
    }

    protected void initBot(){
        bot.setJdaBuilder(JDABuilder.createDefault(""));
        bot.setName(name);
    }

    protected void initConfiguration() {
        File authFile = botServiceFactory.get(PlatformService.class).loadAuthFile();
        try (InputStream in = new FileInputStream(authFile)) {
            this.botConfiguration = mapper.readValue(in, BotConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void initServices() {
        botServiceFactory.createAll();
    }

    protected void provideServices(Bot bot){
        botServiceFactory.connectAll(bot);
        bot.setBotServiceFactory(botServiceFactory);
    }

    protected void provideConfiguration(Bot bot){
        bot.setConfiguration(botConfiguration);
    }

}
