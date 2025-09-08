package bot.command.completer;

import java.util.ArrayList;
import java.util.List;

import bot.command.core.CommandAutoCompleter;
import io.github.gdussine.bot.api.Bot;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class NumberAutoCompleter extends CommandAutoCompleter {

    @Override
    public List<Command.Choice> getChoices(Bot bot, CommandAutoCompleteInteraction interaction) {
        List<Command.Choice> options = new ArrayList<>();
        for(long i = 0; i < 10; i++){
            options.add(new Command.Choice("Le Nombre "+i, i));
        }
    	return options;
    }


}
