package com.github.gdussine.command.core;

import com.github.gdussine.bot.core.BotConfiguration;

import java.util.List;

public class CommandableBotConfiguration extends BotConfiguration {

    protected String commandPrefix;


    public String getCommandPrefix() {
        return commandPrefix;
    }

}
