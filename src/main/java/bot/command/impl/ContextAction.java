package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.completer.ContextAutoCompleter;
import bot.command.core.CommandAction;
import bot.command.exception.CommandActionException;
import bot.context.GuildContextService;
import bot.view.impl.ContextView;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "context", permission = { Permission.ADMINISTRATOR })
public class ContextAction extends CommandAction {

    public GuildContextService getService() {
        return bot.getService(GuildContextService.class);
    }

    @CommandDescription("Insert guild context entry")
    public void insert(
            @CommandOption(description = "Context key", autocompleter = ContextAutoCompleter.class) String key,
            @CommandOption(description = "Context value") String value) throws CommandActionException {
        getService().editContextEntry(interaction.getGuild().getIdLong(), key, value);
        this.view();

    }

    @CommandDescription("View guild context")
    public void view() {
        ContextView view = new ContextView(getService().getDefinedKeys(), bot.getContext(interaction.getGuild()));
        interaction.replyEmbeds(view.render()).queue();
    }

    @CommandDescription("Delete guild context entry")
    public void delete(
            @CommandOption(description = "Context key", autocompleter = ContextAutoCompleter.class) String key)
            throws CommandActionException {

        bot.getService(GuildContextService.class).removeContextEntry(interaction.getGuild().getIdLong(), key);
        this.view();
    }

}
