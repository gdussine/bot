package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bot.context.GuildContext;
import bot.context.GuildContextDeserializer;
import bot.context.GuildContextFileRepository;
import bot.context.GuildContextSupplier;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

public class Bot implements GuildContextSupplier{

    protected JDA jda;
    protected JDABuilder jdaBuilder;
    protected ObjectMapper mapper;
    protected Logger log;
    protected GuildContextSupplier contextSupplier;

    public Bot(){
        this.jdaBuilder = JDABuilder.createDefault("");
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new SimpleModule().addDeserializer(GuildContext.class,new GuildContextDeserializer(this)));
        this.log = LoggerFactory.getLogger(getClass());
    }

    protected void afterLogin(){
        this.contextSupplier = new GuildContextFileRepository(this);
    }


    public void login(String token) {
        jda = jdaBuilder.setToken(token).build();
        try {
            jda.awaitReady();
            this.afterLogin();
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

    public ObjectMapper getMapper() {
        return mapper;
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
