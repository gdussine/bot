package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.context.GuildContextService;
import bot.view.impl.ContextActionView;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "context", permission = { Permission.ADMINISTRATOR })
public class ContextAction extends CommandAction {

    @CommandDescription("Insert guild context entry")
    public void insert(
            @CommandOption(description = "Context key") String key,
            @CommandOption(description = "Context value") String value) {
        bot.getService(GuildContextService.class).setContextEntry(interaction.getGuild(), key, value);
        this.view();
    }

    @CommandDescription("View guild context")
    public void view() {
        interaction.replyEmbeds(new ContextActionView().toContextAllView(bot.getContext(interaction.getGuild())).render()).queue();
    }

    @CommandDescription("Delete guild context entry")
    public void delete(
            @CommandOption(description = "Context key") String key) {
        bot.getService(GuildContextService.class).removeContextEntry(interaction.getGuild(), key);
        this.view();
        
    }

}
