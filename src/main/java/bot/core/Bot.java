package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.command.model.CommandDictionnary;
import bot.context.GuildContext;
import bot.context.GuildContextProvider;
import bot.context.GuildContextService;
import bot.persistence.DatabaseService;
import bot.service.core.AbstractBotService;
import bot.service.core.BotServiceFactory;
import jakarta.persistence.EntityManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

public class Bot implements GuildContextProvider{

    protected JDA jda;
    protected JDABuilder jdaBuilder;
    protected Logger log;
    private BotServiceFactory botServiceFactory;
    private CommandDictionnary commands;
    private BotConfiguration configuration;;


    public Bot() {
        this.log = LoggerFactory.getLogger(getClass());
    }

    public void login() {
        jda = jdaBuilder.setToken(configuration.getDiscordToken()).build();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void logout() {
        jda.shutdownNow();
        try {
            jda.awaitShutdown();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        botServiceFactory.close();
    }

    public JDA getJDA() {
        return jda;
    }

    public JDABuilder getJdaBuilder() {
        return jdaBuilder;
    }

    public BotConfiguration getConfiguration() {
        return configuration;
    }

    public BotServiceFactory getBotServiceFactory() {
        return botServiceFactory;
    }

    @Override
    public GuildContext getContext(long guildId) {
        return this.get(GuildContextService.class).getContext(guildId);
    }

    @Override
    public GuildContext getContext(Guild guild) {
        return this.get(GuildContextService.class).getContext(guild);
    }

    public CommandDictionnary getCommands() {
        return commands;
    }

    public <T extends AbstractBotService> T get(Class<T> serviceClass) {
        return botServiceFactory.get(serviceClass);
    }

    public EntityManager getEntityManager() {
        return this.get(DatabaseService.class).getEntityManager();
    }

    public JDA getJda() {
        return jda;
    }

    public void setJda(JDA jda) {
        this.jda = jda;
    }

    public void setJdaBuilder(JDABuilder jdaBuilder) {
        this.jdaBuilder = jdaBuilder;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public void setBotServiceFactory(BotServiceFactory botServiceFactory) {
        this.botServiceFactory = botServiceFactory;
    }

    public void setCommands(CommandDictionnary commands) {
        this.commands = commands;
    }

    public void setConfiguration(BotConfiguration configuration) {
        this.configuration = configuration;
    }

    
}
