package com.github.gdussine.bot.utils;

import com.github.gdussine.bot.core.Bot;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotExecutionListener extends ListenerAdapter {

    private Logger log = LoggerFactory.getLogger("Bot");
    private Bot bot;

    public BotExecutionListener(Bot bot){
        this.bot = bot;
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        log.info(bot.getClass().getSimpleName()+ " will shutdown");
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info(bot.getClass().getSimpleName()+" is ready");
    }
}
