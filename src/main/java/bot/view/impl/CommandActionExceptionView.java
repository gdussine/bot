package bot.view.impl;

import bot.command.exception.CommandActionException;
import bot.view.BotView;

public class CommandActionExceptionView extends BotView {

    public CommandActionExceptionView(CommandActionException exception) {
        this.template.setTitle(":warning: CommandException");
        this.template.setColor(yellow);
        this.template
                .setDescription("> **%s** try to execute *%s*\n%s".formatted(exception.getAction().getInteraction().getUser().getName(),
                        exception.getAction().getInteraction().getCommandString(),
                        exception.getMessage()));
    }

}
