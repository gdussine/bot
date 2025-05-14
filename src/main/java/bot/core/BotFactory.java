package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.platform.PlatformService;
import bot.service.core.BotListenerFactory;
import bot.service.core.BotServiceFactory;
import net.dv8tion.jda.api.JDABuilder;

public class BotFactory {

    protected Logger log;
    protected String authPath;
    private String name;

    protected BotServiceFactory botServiceFactory;
    protected BotConfiguration botConfiguration;
    protected Bot bot;
	private BotListenerFactory botListenerFactory;

    public BotFactory(String name) {
        this.bot = new Bot();
        this.botServiceFactory = new BotServiceFactory(bot);
        this.botListenerFactory = new BotListenerFactory(bot);
        this.name = name;
        this.log = LoggerFactory.getLogger(getClass());
    }

    public Bot createBot() {
        bot.setJdaBuilder(JDABuilder.createDefault(""));
        bot.setBotServiceFactory(botServiceFactory);
        bot.setName(name);
        botServiceFactory.createAll();
        botListenerFactory.createAll();
        botConfiguration =  botServiceFactory.get(PlatformService.class).getBotConfiguration();
        bot.setConfiguration(botConfiguration);

        return bot;
    }
}
