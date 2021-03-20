package com.github.gdussine.base.command;

import com.github.gdussine.command.checker.FormatCheck;
import com.github.gdussine.command.exception.CommandException;
import com.github.gdussine.command.exception.CommandFormatException;
import com.github.gdussine.command.model.CommandInfo;
import com.github.gdussine.command.model.OwnerCommand;

@CommandInfo(
        name = "shutdown",
        description = "Eteinds le bot",
        alias = {"off", "bye"}
        )
public class ShutdownCommand extends OwnerCommand {
    @Override
    public void checkFormat() throws CommandFormatException {
        FormatCheck.checkCommand(FormatCheck.NO_ARGS, this);
    }

    @Override
    public void checkArguments() throws CommandException {

    }

    @Override
    public void onSuccess() {
        src.getChannel().sendMessage("Bye!").queue(x->{
            src.getJDA().shutdown();
        });
    }
}
