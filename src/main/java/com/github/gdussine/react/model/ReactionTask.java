package com.github.gdussine.react.model;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public abstract class ReactionTask {

    protected ReactionInfo info;

    public ReactionTask(String emote, Message message){
        this.info = new ReactionInfo(emote, message);
    }


    public ReactionTask(Emotes emote, Message message){
        this(emote.getCodePoints(), message);
    }

    public ReactionInfo getInfo() {
        return info;
    }

    public void onAdd(MessageReactionAddEvent event){

    }

    public void onRemove(MessageReactionRemoveEvent event){
    }



}
