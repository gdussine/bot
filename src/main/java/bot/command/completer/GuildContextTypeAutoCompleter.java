package bot.command.completer;

import java.util.List;

import bot.command.core.CommandAutoCompleter;
import bot.context.GuildContextType;
import bot.core.Bot;
import net.dv8tion.jda.api.interactions.commands.Command.Choice;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class GuildContextTypeAutoCompleter extends CommandAutoCompleter {

    @Override
    public List<Choice> getChoices(Bot bot, CommandAutoCompleteInteraction interaction) {
        return List.of(GuildContextType.EMOJI, GuildContextType.ROLE,                GuildContextType.TEXT_CHANNEL, GuildContextType.VOICE_CHANNEL)
                .stream()
                .map(type -> new Choice(type.getType().getSimpleName(), type.getType().getSimpleName()))
                .toList();
    }

}
