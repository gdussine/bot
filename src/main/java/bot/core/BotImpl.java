package bot.core;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.context.GuildContextService;
import bot.persistence.EntityService;
import bot.platform.PlatformService;
import bot.service.BotServiceFactory;
import io.github.gdussine.bot.api.Bot;
import io.github.gdussine.bot.api.BotConfiguration;
import io.github.gdussine.bot.api.BotService;
import io.github.gdussine.bot.api.GuildContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

public class BotImpl implements Bot {

    protected JDA jda;
	protected Logger logger = LoggerFactory.getLogger(getClass());
    protected JDABuilder jdaBuilder;
    protected String name;
    private BotServiceFactory botServiceFactory;
    private DiscordConfiguration configuration;;

    
	public void run() {
		try {
			this.start();
			logger.info("Started.");
		} catch (Exception e) {
			logger.error("Failed to run", e);
			;
		}
	}

	public void shutdown() {
		try {
			this.stop();
			logger.info("Stopped.");
		} catch (Exception e) {
			logger.error("Failed to shutdown", e);
		}
	}
	
    public void start() throws InterruptedException {
        botServiceFactory.startAll();
        jda = jdaBuilder.setToken(configuration.getDiscordToken()).build();
        jda.awaitReady();
    }

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
        return this.getStartedService(GuildContextService.class).getContext(guildId);
    }

    public GuildContext getContext(Guild guild) {
        return this.getStartedService(GuildContextService.class).getContext(guild);

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

    public void setConfiguration(DiscordConfiguration configuration) {
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
    public <T extends BotService> T getStartedService(Class<T> type){
        T service = this.getService(type);
        try {
            service.awaitStart().get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            this.logger.error("{} is not running", service.getName());
        }
        return service;
    }

    public EntityService getEntityService() {
        return getStartedService(EntityService.class);
    }

    @Override
    public <T extends BotConfiguration> T getConfiguration(Class<T> type) {
        return getStartedService(PlatformService.class).getConfiguration(type);
    }

}
