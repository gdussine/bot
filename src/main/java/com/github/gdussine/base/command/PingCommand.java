package com.github.gdussine.base.command;

import com.github.gdussine.command.checker.FormatCheck;
import com.github.gdussine.command.exception.CommandException;
import com.github.gdussine.command.exception.CommandFormatException;
import com.github.gdussine.command.model.Command;
import com.github.gdussine.command.model.CommandInfo;
import com.github.gdussine.hermes.model.NavigableMessage;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

@CommandInfo(
        name = "ping",
        description = "Effectue une requete ping à l'API Discord",
        usage = "",
        alias = {"pong","test"}
)
public class PingCommand extends Command {

    @Override
    public void checkFormat() throws CommandFormatException {
        FormatCheck.checkCommand(FormatCheck.NO_ARGS, this);
    }

    @Override
    public void checkArguments() throws CommandException {

    }

    @Override
    public void onSuccess() {
        src.getJDA().getRestPing().queue(time->{
            src.getChannel().sendMessageFormat("Ping : %dms",time).queue();
        });
    }
}
