package com.github.gdussine.main;

import com.github.gdussine.base.core.BaseBot;
import com.github.gdussine.base.core.BaseConfiguration;
import com.github.gdussine.bot.core.Bot;
import com.github.gdussine.bot.utils.BotBuilder;
import com.github.gdussine.command.core.CommandableBot;
import com.github.gdussine.command.core.CommandableBotConfiguration;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class MainApp {

    public static void main(String[] args) throws IOException, LoginException, InterruptedException {
        Bot bot = new BotBuilder(BaseBot.class, BaseConfiguration.class).build("bot.json");
        bot.start();
    }
}
