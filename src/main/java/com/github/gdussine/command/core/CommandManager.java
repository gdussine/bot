package com.github.gdussine.command.core;

import com.github.gdussine.command.model.Command;
import com.github.gdussine.command.model.CommandInfo;
import com.github.gdussine.command.model.CommandReceivedEvent;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.LoggerFactory;


import java.util.*;

public class CommandManager {

    private Map<String, Class<? extends Command>> commandMap;
    private String prefix;

    public CommandManager(CommandableBotConfiguration configuration){
        this.commandMap = new HashMap<String, Class<? extends Command>>();
        this.prefix = configuration.getCommandPrefix();
    }

    public void addCommand(Class<? extends Command> commandClass){
        CommandInfo info = commandClass.getAnnotation(CommandInfo.class);
        for(String alias : info.alias()){
            addOneAlias(alias, commandClass);
        }
        addOneAlias(info.name(), commandClass);
    }

    public CommandReceivedEvent getCommandReceivedEvent(Message message){
        if(message.getAuthor().isBot())
            return null;
        String content = message.getContentDisplay();
        String name = content.split(" ")[0].substring(prefix.length());
        if(!isRegistered(content, name))
            return null;
        Command command = getCommandByAlias(name);
                setUpCommand(command, message);
        return new CommandReceivedEvent(message.getJDA(), message.getJDA().getResponseTotal(), message, command);
    }

    private void addOneAlias(String alias, Class<? extends Command> commandClass){
        if(commandMap.containsKey(alias))
            LoggerFactory.getLogger(this.getClass()).warn("Command alias "+alias+" already exist");
        commandMap.put(alias, commandClass);
    }

    private boolean isRegistered(String content, String name){
        return content.startsWith(prefix) && commandMap.containsKey(name);
    }

    public Class<? extends Command> getCommandClassByAlias(String alias){
        return commandMap.get(alias);
    }

    public Command getCommandByAlias(String alias){
        try {
            return commandMap.get(alias).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<CommandInfo> getInfos(){
        Set<CommandInfo> res = new HashSet<>();
        for(Map.Entry<String, Class<? extends Command>>entry : commandMap.entrySet()){
            res.add(entry.getValue().getAnnotation(CommandInfo.class));
        }
        return res;
    }



    private void setUpCommand(Command command, Message message){
        command.setUp(message);
    }


    @Override
    public String toString() {
        String bf = "";
        for(Map.Entry<String, Class<? extends Command>> entry : commandMap.entrySet()){
            bf += entry.getKey() + " -> " + entry.getValue().getSimpleName() + "\n";
        }
        return "CommandManager{\n" +bf + "}";
    }
}
