package bot.command.impl;

import java.util.List;

import bot.api.Bot;
import bot.command.core.CommandAutoCompleter;
import bot.context.GuildContextService;
import net.dv8tion.jda.api.interactions.commands.Command.Choice;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class ContextAutoCompleter extends CommandAutoCompleter {

    @Override
    public List<Choice> getChoices(Bot bot, CommandAutoCompleteInteraction interaction) {
        return bot.getService(GuildContextService.class).getKeys().stream().map(x->new Choice(x.getName(),x.getName())).toList();
    }

}
