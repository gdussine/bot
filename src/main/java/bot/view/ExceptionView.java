package bot.view;

import java.util.Arrays;

import bot.command.core.CommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ExceptionView extends BotView {

    protected Exception exception;

    public ExceptionView(Exception exception) {
        this.exception = exception;
        this.template.setTitle(":warning:  %s".formatted(exception.getClass().getSimpleName()));
        this.template.setDescription(exception.getMessage());
        StringBuilder sb = new StringBuilder();
        Arrays.stream(exception.getStackTrace()).forEach(x->sb.append(x.toString()).append("\n"));
        this.template.addField("Exception details",sb.subSequence(0, Math.min(sb.length(), 500)).toString(), true);
        this.template.setColor(yellow);
    }

    public ExceptionView toCommandView(){
        CommandException commandException = (CommandException) exception;
        SlashCommandInteractionEvent i = commandException.getAction().getInteraction();
        String field = "Command: %s\nInput: %s\nUser: %s".formatted(i.getSubcommandName(), i.getCommandString(), i.getUser().getEffectiveName());
        this.template.addField("Command details", field, true);
        return this;
    }

}
