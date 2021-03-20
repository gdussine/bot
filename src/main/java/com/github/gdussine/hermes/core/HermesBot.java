package com.github.gdussine.hermes.core;

import com.github.gdussine.react.core.ReactionBot;

public class HermesBot extends ReactionBot {

    private NavigableMessageManager navigableMessageManager;

    public HermesBot(HermesBotConfiguration config) {
        super(config);
        this.navigableMessageManager = new NavigableMessageManager();
    }

    public NavigableMessageManager getNavigableMessageManager() {
        return navigableMessageManager;
    }

    public static HermesBot getInstance(){
        return (HermesBot)HermesBot.instance;
    }
}
