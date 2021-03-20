package com.github.gdussine.hermes.reaction;

import com.github.gdussine.hermes.core.HermesBot;
import com.github.gdussine.react.model.Emotes;
import com.github.gdussine.react.model.ReactionTask;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class NextReactionTask extends ReactionTask {

    public NextReactionTask( Message message) {
        super(Emotes.ARROW_RIGHT, message);
    }

    @Override
    public void onAdd(MessageReactionAddEvent event) {
        HermesBot.getInstance().getNavigableMessageManager().next(event.getMessageId(), event.getUser());
    }
}