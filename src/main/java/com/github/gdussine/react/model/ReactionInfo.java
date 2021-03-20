package com.github.gdussine.react.model;

import net.dv8tion.jda.api.entities.Message;

import java.util.Objects;

public class ReactionInfo {

    private String emote;
    private Message message;
    private String messageId;

    public ReactionInfo(String emote, Message message){
        this.message = message;
        this.emote = emote;
        this.messageId = message.getId();
    }

    public ReactionInfo(String emote, String messageId){
        this.message = null;
        this.emote = emote;
        this.messageId = messageId;
    }



    public String getEmote() {
        return emote;
    }

    public Message getMessage() {
        return message;
    }

    public String getMessageId() {
        return messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionInfo that = (ReactionInfo) o;
        return Objects.equals(emote, that.emote) &&
                Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emote, messageId);
    }
}
