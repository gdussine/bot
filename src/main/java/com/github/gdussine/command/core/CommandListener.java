package com.github.gdussine.command.core;

import com.github.gdussine.command.model.CommandReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {


    CommandManager commandManager;

    public CommandListener(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        CommandReceivedEvent commandEvent = this.commandManager.getCommandReceivedEvent(event.getMessage());
        if(commandEvent!=null) {
            this.onCommandReceived(commandEvent);
            commandEvent.getCommand().run();
        }
    }


    public void onCommandReceived(@NotNull CommandReceivedEvent event){

    }

}
