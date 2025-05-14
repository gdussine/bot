package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.command.core.CommandException;
import bot.context.GuildContextEntry;
import bot.context.GuildContextService;
import bot.view.BotView;
import bot.view.SystemView;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "system", permission = { Permission.ADMINISTRATOR })
public class SystemAction extends CommandAction {

    @Override
    public void check() throws CommandException {
        this.checkOwner();
    }

    @CommandDescription("Show network indicators")
    public void network(
            @CommandOption(description = "Visible by all", required = false) Boolean ephemeral) {
        interaction.getJDA().getRestPing().submit().thenAccept(x -> {
            BotView view = new SystemView().toNetworkView(x);
            interaction.replyEmbeds(view.render()).setEphemeral(ephemeral == null ? false : ephemeral)
                    .submit();
        });
    }
    
    @CommandDescription("Edit guild context")
    public void context(
    		@CommandOption(description = "Context key") String key,
    		@CommandOption(description = "Context value", required = false) String value) {
    	if(value == null) {
    		String output = bot.getContext(interaction.getGuild()).getString(key);
    		interaction.reply(key +" -> " +output).queue();;
    	} else {
	    	GuildContextEntry entry = new GuildContextEntry(interaction.getGuild().getIdLong(), key, value);
	    	bot.get(GuildContextService.class).updateEntry(entry);
    		interaction.reply(entry.getContextKey() +" -> " +entry.getContextValue()).queue();
    	}
    }
 

    @CommandDescription("Show service status")
    public void services() {
        BotView view = new SystemView().toServicesView(bot.getBotServiceFactory().getAll());
        interaction.replyEmbeds(view.render()).submit();
    }

    @CommandDescription("Shutdown the bot")
    public void shutdown() {
        interaction.reply("Bye! :wave:").queue(x -> {
            bot.logout();
        });
    }
}
