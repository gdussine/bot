package bot.view;

import bot.command.core.CommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ExceptionView extends BotView {

    protected Exception exception;

    public ExceptionView(Exception exception) {
        this.exception = exception;
        this.template.setTitle(":warning: - %s".formatted(exception.getClass().getSimpleName()));
        this.template.setDescription(exception.getMessage());
        this.template.setColor(yellow);
    }

    public ExceptionView toCommandView(){
        CommandException commandException = (CommandException) exception;
        SlashCommandInteractionEvent i = commandException.getAction().getInteraction();
        String field = "Command: %s\nInput: %s\nUser: %s".formatted(i.getSubcommandName(), i.getCommandString(), i.getUser().getEffectiveName());
        this.template.addField("Details", field, false);
        return this;
    }

}
