package com.github.gdussine.command.model;

import com.github.gdussine.command.exception.CommandException;
import com.github.gdussine.command.exception.CommandFormatException;
import com.github.gdussine.command.exception.CommandPermissionException;
import net.dv8tion.jda.api.entities.Message;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Predicate;


@CommandInfo(name = "command", description = "description",usage = "usage")
public abstract class Command {


    protected Message src;
    protected String name;
    protected String category;
    protected String[] alias;
    protected String description;
    protected String usage;
    protected String[] args;

    public Command(){
        CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);
        this.name = info.name();
        this.description = info.description();
        this.usage = info.usage();
        this.alias = info.alias();
        this.category = info.category();
    }

    public void setUp(Message src){
        this.src = src;
        String[] fullArgs = src.getContentDisplay().split(" ");
        this.args = Arrays.copyOfRange(fullArgs,1,fullArgs.length);

    }

    public Message getSrc() {
        return src;
    }

    public String[] getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getArgs() {
        return args;
    }
    public abstract void checkFormat() throws CommandFormatException;


    public abstract void checkArguments() throws CommandException;

    public abstract void onSuccess();

    public void onError(CommandException exception){
        src.getChannel().sendMessage(exception.getMessage()).queue();
    }

    public void run(){
        try {
            this.checkFormat();
            this.checkArguments();
            this.onSuccess();
        } catch (CommandException exception) {
            this.onError(exception);
        }

    }



}
