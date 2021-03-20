package com.github.gdussine.command.exception;

import com.github.gdussine.command.model.Command;

public class CommandException extends Exception{

    private Command command;

    public CommandException(String message, Command command){
        super(message);
        this.command = command;
    }

}
