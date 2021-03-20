package com.github.gdussine.react.core;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReactionListener extends ListenerAdapter {


    private ReactionManager manager;

    public ReactionListener(ReactionManager manager){
        this.manager = manager;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        manager.reactionAdd(event);
        System.out.println(event.getReaction().getReactionEmote().getAsCodepoints());
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        manager.reactionRemove(event);
    }




}
