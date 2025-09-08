package bot.command.completer;

import java.util.List;

import bot.command.core.CommandAutoCompleter;
import bot.context.GuildContextService;
import io.github.gdussine.bot.api.Bot;
import net.dv8tion.jda.api.interactions.commands.Command.Choice;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class ContextAutoCompleter extends CommandAutoCompleter {

    @Override
    public List<Choice> getChoices(Bot bot, CommandAutoCompleteInteraction interaction) {
        return bot.getService(GuildContextService.class).getDefinedKeys().stream()
                .map(x -> new Choice(x.getContextKey(), x.getContextKey())).toList();
    }

}
