package com.github.gdussine.command.model;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CommandReceivedEvent extends MessageReceivedEvent {

    private Command command;


    public CommandReceivedEvent(@NotNull JDA api, long responseNumber, @NotNull Message message, Command command) {
        super(api, responseNumber, message);
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
