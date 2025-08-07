package bot.core;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import bot.api.Bot;
import bot.api.GuildContext;
import bot.context.GuildContextService;
import bot.api.BotService;
import bot.service.BotServiceFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

public class BotImpl extends BotLaunchable implements Bot {

    protected JDA jda;
    protected JDABuilder jdaBuilder;
    protected String name;
    private BotServiceFactory botServiceFactory;
    private BotConfiguration configuration;;

    @Override
    public void start() throws InterruptedException {
        botServiceFactory.startAll();
        jda = jdaBuilder.setToken(configuration.getDiscordToken()).build();
        jda.awaitReady();
    }

    @Override
    public void stop() throws InterruptedException {
        botServiceFactory.stopAll();
        jda.shutdownNow();
        jda.awaitShutdown();
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

    public GuildContext getContext(long guildId) {
        return this.getService(GuildContextService.class).getContext(guildId);
    }

    public GuildContext getContext(Guild guild) {
        return this.getService(GuildContextService.class).getContext(guild);
    }

    public <T extends BotService> T getService(Class<T> serviceClass) {
        return botServiceFactory.get(serviceClass);
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

    public void setBotServiceFactory(BotServiceFactory botServiceFactory) {
        this.botServiceFactory = botServiceFactory;
    }

    public void setConfiguration(BotConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Collection<BotService> getServices() {
        return botServiceFactory.getAll();
    }

    @Override
    public <T extends BotService> T getRunningService(Class<T> type){
        T service = this.getService(type);
        try {
            service.getHandler().awaitRunning().get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            service.getLogger().error("{} is not running", service.getName());
        }
        return service;
    }

}
