package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bot.api.Bot;
import bot.platform.PlatformService;
import bot.service.BotListenerFactory;
import bot.service.BotServiceFactory;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BotFactory {

    protected Logger log;
    protected String authPath;
    private String name;

    protected BotServiceFactory botServiceFactory;
    protected BotConfiguration botConfiguration;
    protected BotImpl bot;
    private BotListenerFactory botListenerFactory;

    public BotFactory(String name) {
        this.bot = new BotImpl();
        this.botServiceFactory = new BotServiceFactory(bot);
        this.botListenerFactory = new BotListenerFactory(bot);
        this.name = name;
        this.log = LoggerFactory.getLogger(getClass());
    }

    public Bot createBot() {
        JDABuilder jdaBuilder = JDABuilder.createDefault("").enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);
        bot.setJdaBuilder(jdaBuilder);
        bot.setBotServiceFactory(botServiceFactory);
        bot.setName(name);
        botServiceFactory.createAll();
        botListenerFactory.createAll();
        botConfiguration =  botServiceFactory.get(PlatformService.class).getBotConfiguration();
        bot.setConfiguration(botConfiguration);
        return bot;
    }
}
