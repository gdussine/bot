package com.github.gdussine.hermes.reaction;

import com.github.gdussine.hermes.core.HermesBot;
import com.github.gdussine.react.model.Emotes;
import com.github.gdussine.react.model.ReactionTask;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class PreviousReactionTask extends ReactionTask {
    public PreviousReactionTask( Message message) {
        super(Emotes.ARROW_LEFT, message);
    }

    @Override
    public void onAdd(MessageReactionAddEvent event) {
        HermesBot.getInstance().getNavigableMessageManager().previous(event.getMessageId(), event.getUser());
    }
}
