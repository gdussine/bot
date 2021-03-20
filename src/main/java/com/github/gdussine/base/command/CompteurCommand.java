package com.github.gdussine.base.command;

import com.github.gdussine.command.exception.CommandException;
import com.github.gdussine.command.exception.CommandFormatException;
import com.github.gdussine.command.exception.CommandPermissionException;
import com.github.gdussine.command.model.CommandInfo;
import com.github.gdussine.command.model.GuildCommand;
import com.github.gdussine.hermes.core.HermesBot;
import com.github.gdussine.hermes.model.NavigableMessage;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "compteur")
public class CompteurCommand extends GuildCommand {


    @Override
    public void checkPermission() throws CommandPermissionException {

    }

    @Override
    public void checkFormat() throws CommandFormatException {

    }

    @Override
    public void checkArguments() throws CommandException {

    }

    @Override
    public void onSuccess() {
        List<Message> messages = new ArrayList<>();
        for(int i= 0; i<10; i++){
            MessageBuilder messageBuilder = new MessageBuilder("Page : "+i);
            messages.add(messageBuilder.build());
        }
        HermesBot.getInstance().getNavigableMessageManager().init(messages, this.src.getTextChannel());

    }
}
