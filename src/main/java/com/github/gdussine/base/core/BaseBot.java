package com.github.gdussine.base.core;

import com.github.gdussine.base.command.CompteurCommand;
import com.github.gdussine.base.command.HelpCommand;
import com.github.gdussine.base.command.PingCommand;
import com.github.gdussine.base.command.ShutdownCommand;
import com.github.gdussine.hermes.core.HermesBot;
import com.github.gdussine.react.core.ReactionBot;

public class BaseBot extends HermesBot {

    public BaseBot(BaseConfiguration config) {
        super(config);
        addCommands(PingCommand.class, ShutdownCommand.class, HelpCommand.class, CompteurCommand.class);
    }
}
