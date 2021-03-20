package com.github.gdussine.base.command;

import com.github.gdussine.command.checker.FormatCheck;
import com.github.gdussine.command.checker.PermissionCheck;
import com.github.gdussine.command.core.CommandManager;
import com.github.gdussine.command.core.CommandableBot;
import com.github.gdussine.command.exception.CommandException;
import com.github.gdussine.command.exception.CommandFormatException;
import com.github.gdussine.command.exception.CommandPermissionException;
import com.github.gdussine.command.model.Command;
import com.github.gdussine.command.model.CommandInfo;
import com.github.gdussine.command.model.GuildCommand;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Set;


@CommandInfo(
        name = "help",
        description = "Affiche l'aide",
        usage = "[ commandName ]",
        alias = "whatis"
)
public class HelpCommand extends GuildCommand {

    private CommandInfo info;
    private Set<CommandInfo> infoSet;
    private CommandManager commandManager;

    @Override
    public void checkPermission() throws CommandPermissionException {
        PermissionCheck.checkCommand(PermissionCheck.NO_PERMS, this);
    }

    @Override
    public void checkFormat() throws CommandFormatException {
        FormatCheck.checkCommand(FormatCheck.ONE_OR_LESS_ARG, this);
    }

    @Override
    public void checkArguments() throws CommandException {
        CommandManager commandManager = CommandableBot.getInstance().getCommandManager();
        if(args.length == 1){
            String commandName = args[0];
             Class<? extends Command> commandClass = commandManager.getCommandClassByAlias(commandName);
            if(commandClass == null)
                throw new CommandException("The command "+commandName+" doesn't exist", this);
            info = commandClass.getDeclaredAnnotation(CommandInfo.class);
            if(info == null){
                throw new CommandException("Info for command "+commandName+" aren't available", this);
            }
        } else {
            infoSet = commandManager.getInfos();
        }
    }

    @Override
    public void onSuccess() {
        if(info == null) {

            EmbedBuilder eb = new EmbedBuilder().setTitle("Command list");
            for(CommandInfo info : infoSet){
                eb.appendDescription(String.format("**%s**", info.name()));
                eb.appendDescription(String.format(" : %s", info.description())+"\n");
            }
            src.getChannel().sendMessage(eb.build()).queue();
        }


    }
}
