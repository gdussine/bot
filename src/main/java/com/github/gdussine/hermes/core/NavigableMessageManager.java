package com.github.gdussine.hermes.core;

import com.github.gdussine.hermes.core.HermesBot;
import com.github.gdussine.hermes.model.NavigableMessage;
import com.github.gdussine.hermes.reaction.NextReactionTask;
import com.github.gdussine.hermes.reaction.PreviousReactionTask;
import com.github.gdussine.react.model.Emotes;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigableMessageManager {

    private final Map<String, NavigableMessage> messageMap;

    public NavigableMessageManager(){
        this.messageMap = new HashMap<>();
    }

    public void register(String id, NavigableMessage message){
        messageMap.put(id, message);
    }

    public void init(List<Message> messages, TextChannel channel){
        Message msg = channel.sendMessage(messages.get(0)).complete();
        msg.addReaction(Emotes.ARROW_LEFT.getCodePoints()).queue(v->
                msg.addReaction(Emotes.ARROW_RIGHT.getCodePoints()).queue());
        NavigableMessage navigableMessage = new NavigableMessage(messages, channel);
        navigableMessage.setMessageSend(msg);
        HermesBot.getInstance().getReactionManager().addTask(new NextReactionTask(msg));
        HermesBot.getInstance().getReactionManager().addTask(new PreviousReactionTask(msg));
        this.register(msg.getId(), navigableMessage);
    }

    private void update(NavigableMessage nm, User user, Emotes emote){
        Message messageSend = nm.getMessageSend().editMessage(nm.getMessage()).complete();
        nm.setMessageSend(messageSend);
        messageSend.removeReaction(emote.getCodePoints(), user).queue();
    }

    public void next(String id, User user){
        NavigableMessage nm = messageMap.get(id);
        nm.next();
        update(nm, user, Emotes.ARROW_RIGHT);

    }

    public void previous(String id, User user){
        NavigableMessage nm = messageMap.get(id);
        nm.previous();
        update(nm, user, Emotes.ARROW_LEFT);

    }

}