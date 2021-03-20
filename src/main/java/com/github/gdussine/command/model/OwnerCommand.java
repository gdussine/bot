package com.github.gdussine.command.model;

import com.github.gdussine.bot.core.Bot;

public abstract class OwnerCommand extends Command{

    @Override
    public void run() {
        if(!Bot.getInstance().getConfig().getIdOwner().equals(src.getAuthor().getId()))
            return;
        super.run();
    }
}
