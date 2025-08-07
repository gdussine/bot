package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.command.exception.CommandActionException;
import bot.context.GuildContextException;
import bot.context.GuildContextService;
import bot.view.impl.ContextActionView;
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

        try {
            getService().setContextEntry(interaction.getGuild(), key, value);
        } catch (GuildContextException e) {
            throw new CommandActionException(e.getMessage(),this);
        }
        this.view();

    }

    @CommandDescription("View guild context")
    public void view() {
        interaction
                .replyEmbeds(new ContextActionView()
                        .toContextAllView(getService().getKeys(), bot.getContext(interaction.getGuild())).render())
                .queue();
    }

    @CommandDescription("Delete guild context entry")
    public void delete(
            @CommandOption(description = "Context key", autocompleter = ContextAutoCompleter.class) String key) {
        bot.getService(GuildContextService.class).removeContextEntry(interaction.getGuild(), key);
        this.view();
    }

    

}
