package bot.command.core;

import bot.core.Bot;
import bot.core.BotService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class CommandManager extends BotService {

    private CommandMap commandMap;

    public CommandManager() {
        commandMap = CommandMap.create();
    }

    @Override
    public void connect(Bot bot) {
        super.connect(bot, new CommandListener());
    }

    public void init(Guild guild) {
        guild.updateCommands().addCommands(commandMap.getCommands()).queue();
    }

    public void autocomplete(CommandAutoCompleteInteraction interaction) {
        CommandEntry entry = commandMap.get(interaction.getName(), interaction.getSubcommandName());
        entry.getOptionInfo().stream().filter(x -> x.getName().equals(interaction.getFocusedOption().getName()))
                .forEach(option -> option.getAutocompleter().accept(interaction));
    }

    public void execute(SlashCommandInteractionEvent event) {
        CommandEntry entry = commandMap.get(event.getName(), event.getSubcommandName());
        CommandAction action = entry.getCommandAction();
        Object[] parameters = entry.getCommandParameters(event);
        try {
            action.hydrate(this.getBot(), event);
            action.check();
            entry.getMethod().invoke(action, parameters);
        } catch (CommandException e) {
            action.replyException(e).queue();
        } catch (Exception e) {
            action.replyException(e).queue();
        }
    }

}
