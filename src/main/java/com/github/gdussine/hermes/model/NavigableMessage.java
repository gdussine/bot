package com.github.gdussine.hermes.model;

import com.github.gdussine.react.core.ReactionBot;
import com.github.gdussine.react.core.ReactionManager;
import com.github.gdussine.react.model.Emotes;
import com.github.gdussine.react.model.ReactionTask;
import net.dv8tion.jda.api.entities.Invite.Channel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.List;


public class NavigableMessage{

    private List<Message> messages;
    private TextChannel channel;
    private Message messageSend;
    private int selectedIndex;

    public NavigableMessage( List<Message> messages, TextChannel channel){
        this.messages = messages;
        this.selectedIndex = 0;
        this.channel = channel;
    }

    public void setMessageSend(Message messageSend) {
        this.messageSend = messageSend;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public Message getMessageSend() {
        return messageSend;
    }

    public Message getMessage(){
        return this.messages.get(selectedIndex);
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void previous(){
        this.selectedIndex = ((this.selectedIndex - 1) + messages.size())%messages.size();
    }

    public void next(){
        this.selectedIndex = ((this.selectedIndex + 1) + messages.size())%messages.size();
    }

}
