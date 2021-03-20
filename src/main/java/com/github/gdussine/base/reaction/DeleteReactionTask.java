package com.github.gdussine.base.reaction;

import com.github.gdussine.react.core.ReactionBot;
import com.github.gdussine.react.model.Emotes;
import com.github.gdussine.react.model.ReactionTask;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class DeleteReactionTask extends ReactionTask {

    public DeleteReactionTask(Message message) {
        super(Emotes.X.getCodePoints(), message);
    }

    @Override
    public void onAdd(MessageReactionAddEvent event) {
        info.getMessage().delete().queue();
    }

    public static void setUpDeleteReaction(Message message){
        message.addReaction(Emotes.X.getCodePoints()).queue();
        DeleteReactionTask deleteReactionTask = new DeleteReactionTask(message);
        ReactionBot.getInstance().getReactionManager().addTask(deleteReactionTask);
    }
}
