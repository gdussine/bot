package com.github.gdussine.command.exception;

import com.github.gdussine.command.model.Command;

public class CommandPermissionException extends CommandException {

    private final static String format = "You don't have permission for this command";

    public CommandPermissionException(Command command) {
        super(String.format(format), command);
    }
}
