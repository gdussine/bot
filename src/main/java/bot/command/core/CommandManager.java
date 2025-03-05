package bot.command.core;

import bot.command.annotations.CommandOption;
import bot.core.Bot;
import bot.core.BotService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class CommandManager extends BotService {

    private final static String COMMAND_PACKAGE = "bot.command.impl";
    private CommandMap map;

    public CommandManager(Bot bot) {
        super(bot);
        this.map = CommandMap.create(COMMAND_PACKAGE);
    }

    public void init(Guild guild) {
        guild.updateCommands().addCommands(map.getCommands()).queue();
    }

    public void autocomplete(CommandAutoCompleteInteraction interaction){
        CommandEntry entry = map.get(interaction.getName(), interaction.getSubcommandName());
        CommandOption option = entry.getOptions().stream().filter(x->x.name().equals(interaction.getFocusedOption().getName())).findAny().orElse(null);
        option.autocomplete().getCompleter().accept(interaction);
        
    }

    public void execute(SlashCommandInteractionEvent event) {
        CommandEntry entry = map.get(event.getName(), event.getSubcommandName());
        CommandAction action = entry.toCommandAction(bot, event);
        Object[] parameters = entry.toCommandParameters(event);
        try {
            action.check();
            entry.getMethod().invoke(action, parameters);
        } catch (CommandException e) {
            action.replyException(e);
        } catch(Exception e){
            action.replyException(e);
        }
    }

}
