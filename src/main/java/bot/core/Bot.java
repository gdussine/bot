package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.command.model.CommandDictionnary;
import bot.context.GuildContext;
import bot.context.GuildContextProvider;
import bot.persistence.DatabaseService;
import bot.service.core.AbstractBotService;
import bot.service.core.BotServiceFactory;
import jakarta.persistence.EntityManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

public class Bot implements BotProxy {

    private static Bot instance;

    protected JDA jda;
    protected JDABuilder jdaBuilder;
    protected Logger log;
    protected GuildContextProvider contextSupplier;
    private BotServiceFactory botServiceFactory;
    private CommandDictionnary commands;
    private BotConfiguration configuration;;

    public Bot( JDABuilder jdaBuilder, BotConfiguration configuration, BotServiceFactory botServiceFactory, CommandDictionnary commands) {
        this.log = LoggerFactory.getLogger(getClass());
        this.jdaBuilder = jdaBuilder;
        this.botServiceFactory = botServiceFactory;
        this.commands = commands;
        this.configuration = configuration;
        instance = this;
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

    public void setContextSupplier(GuildContextProvider contextSupplier) {
        this.contextSupplier = contextSupplier;
    }

    @Override
    public GuildContext getContext(long guildId) {
        return contextSupplier.getContext(guildId);
    }

    @Override
    public GuildContext getContext(Guild guild) {
        return contextSupplier.getContext(guild);
    }

    public CommandDictionnary getCommands() {
        return commands;
    }


    public static Bot getInstance() {
        return instance;
    }


    @Override
    public <T extends AbstractBotService> T get(Class<T> serviceClass) {
        return botServiceFactory.get(serviceClass);
    }


    @Override
    public EntityManager getEntityManager() {
        return this.get(DatabaseService.class).getEntityManager();
    }
}
