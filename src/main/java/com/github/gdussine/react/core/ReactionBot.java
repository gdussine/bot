package com.github.gdussine.react.core;

import com.github.gdussine.command.core.CommandableBot;

public class ReactionBot extends CommandableBot {

    private ReactionListener reactionListener;
    private ReactionManager reactionManager;

    public ReactionBot(ReactionBotConfiguration config) {
        super(config);
        this.reactionManager = new ReactionManager();
        this.reactionListener = new ReactionListener(reactionManager);
        this.jdaBuilder.addEventListeners(reactionListener);
    }

    public static ReactionBot getInstance(){
        return (ReactionBot) instance;
    }

    public ReactionManager getReactionManager() {
        return reactionManager;
    }

    @Override
    public ReactionBotConfiguration getConfig(){
        return (ReactionBotConfiguration) config;
    }
}
