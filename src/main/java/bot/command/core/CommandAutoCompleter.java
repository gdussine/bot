package bot.command.core;

import java.util.List;

import bot.core.Bot;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public abstract class CommandAutoCompleter {

    public void accept(Bot bot, CommandAutoCompleteInteraction interaction) {
        List<Command.Choice> choices = getChoices(bot, interaction).stream()
                .filter(x -> x.getName().startsWith(interaction.getFocusedOption().getValue()))
                .toList();
        interaction.replyChoices(choices).queue();
    }

    public abstract List<Command.Choice> getChoices(Bot bot, CommandAutoCompleteInteraction interaction);

}
