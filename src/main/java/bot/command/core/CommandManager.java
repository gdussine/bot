package bot.command.core;

import bot.command.model.CommandInfo;
import bot.service.core.BotService;
import bot.service.core.AbstractBotService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

@BotService(listener = CommandListener.class)
public class CommandManager extends AbstractBotService {

    public void init(Guild guild) {
        guild.updateCommands().addCommands(this.getBot().getCommands().getDataSet()).queue();
    }

    public void autocomplete(CommandAutoCompleteInteraction interaction) {
        CommandInfo info = this.getBot().getCommands().get(interaction.getName(), interaction.getSubcommandName());
        info.getOptions().stream().filter(x -> x.getName().equals(interaction.getFocusedOption().getName()))
                .forEach(option -> option.getAutocompleter().accept(this.getBot(), interaction));
    }

    public void execute(SlashCommandInteractionEvent event) {
        CommandInfo info = this.getBot().getCommands().get(event.getName(), event.getSubcommandName());
        CommandAction action = info.getCommandAction();
        Object[] parameters = info.getCommandParameters(event);
        try {
            action.hydrate(this.getBot(), event);
            action.check();
            info.getMethod().invoke(action, parameters);
        } catch (CommandException e) {
            action.replyException(e).queue();
        } catch (Exception e) {
            action.replyException(e).queue();
        }
    }

}
