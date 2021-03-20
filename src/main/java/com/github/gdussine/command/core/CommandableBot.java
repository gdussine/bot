package com.github.gdussine.command.core;

import com.github.gdussine.bot.core.Bot;
import com.github.gdussine.command.model.Command;

public class CommandableBot extends Bot {

    CommandManager commandManager;
    CommandListener commandListener;

    public CommandableBot(CommandableBotConfiguration config) {
        super(config);
        commandManager = new CommandManager(config);
        commandListener = new CommandListener(commandManager);
        this.jdaBuilder.addEventListeners(commandListener);
    }

    public void addCommands(Class<? extends Command> ...commands){
        for(Class<? extends Command> command : commands)
            commandManager.addCommand(command);
    }


    public static CommandableBot getInstance(){
        return (CommandableBot) instance;
    }

    @Override
    public CommandableBotConfiguration getConfig(){
        return (CommandableBotConfiguration) config;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public CommandListener getCommandListener() {
        return commandListener;
    }
}
