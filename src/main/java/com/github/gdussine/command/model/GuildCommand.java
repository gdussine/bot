package com.github.gdussine.command.model;

import com.github.gdussine.command.exception.CommandException;
import com.github.gdussine.command.exception.CommandPermissionException;
import net.dv8tion.jda.api.entities.Message;

public abstract class GuildCommand extends Command{

    public abstract void checkPermission() throws CommandPermissionException;


    @Override
    public void run() {
        if(!src.isFromGuild()){
            return;
        }
        try{
            checkPermission();
        } catch (CommandException e){
            this.onError(e);
        }
        super.run();
    }
}
