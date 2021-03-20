package com.github.gdussine.react.core;

import com.github.gdussine.react.model.ReactionInfo;
import com.github.gdussine.react.model.ReactionTask;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.HashMap;
import java.util.Map;

public class ReactionManager {


    private Map<ReactionInfo, ReactionTask> reactionMap;

    public ReactionManager(){
        this.reactionMap = new HashMap<>();
    }

    public ReactionTask getTask(String emote, String messageId){
        ReactionTask rt = reactionMap.get(new ReactionInfo(emote, messageId));
        return rt;
    }

    public void addTask(ReactionTask task){
        reactionMap.put(task.getInfo(), task);
    }

    public void reactionAdd(MessageReactionAddEvent addEvent){
        if(addEvent.getUser().isBot())
            return;
        ReactionTask rt = getTask( addEvent.getReactionEmote().getAsCodepoints(),  addEvent.getMessageId());

        if(rt == null)
            return;
        rt.onAdd(addEvent);
    }

    public void reactionRemove(MessageReactionRemoveEvent removeEvent){
        if(removeEvent.getUser().isBot())
            return;
        ReactionTask rt = getTask( removeEvent.getReactionEmote().getAsCodepoints(),  removeEvent.getMessageId());
        if(rt == null)
            return;
        rt.onRemove(removeEvent);
    }



}
