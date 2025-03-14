package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.context.GuildContext;
import bot.context.GuildContextProvider;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

public class Bot implements GuildContextProvider {

    protected JDA jda;
    protected JDABuilder jdaBuilder;
    protected Logger log;
    protected GuildContextProvider contextSupplier;
    private BotServices services;

    public Bot( JDABuilder jdaBuilder, BotServices services) {
        this.log = LoggerFactory.getLogger(getClass());
        this.jdaBuilder = jdaBuilder;
        this.services = services;
    }

    public void login(String token) {
        jda = jdaBuilder.setToken(token).build();
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
    }

    public JDA getJDA() {
        return jda;
    }

    public JDABuilder getJdaBuilder() {
        return jdaBuilder;
    }

    public BotServices services(){
        return services;
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
}
