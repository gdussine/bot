package bot.command.core;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class NumberAutoCompleter extends CommandAutoCompleter {

    @Override
    public void accept(CommandAutoCompleteInteraction interaction) {
        List<Command.Choice> options = new ArrayList<>();
        for(long i = 0; i < 10; i++){
            options.add(new Command.Choice("Le Nombre "+i, i));
        }
    	interaction.replyChoices(options).queue();
    }

}
