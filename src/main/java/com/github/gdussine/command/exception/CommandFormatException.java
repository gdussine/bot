package com.github.gdussine.command.exception;

import com.github.gdussine.command.core.CommandableBot;
import com.github.gdussine.command.model.Command;

public class CommandFormatException extends CommandException{

    private final static String format = "Invalid format expected : %s%s %s";

    public CommandFormatException(Command command) {
        super(String.format(format, CommandableBot.getInstance().getConfig().getCommandPrefix(), command.getName(), command.getUsage()), command);
    }
}
