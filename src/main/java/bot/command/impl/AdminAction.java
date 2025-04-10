package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.completer.GuildContextTypeAutoCompleter;
import bot.command.core.CommandAction;
import bot.context.GuildContext;
import bot.context.GuildContextService;
import bot.context.GuildContextType;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "admin", permission = { Permission.ADMINISTRATOR })
public class AdminAction extends CommandAction {

    @CommandDescription("PING request to Discord API")
    public void ping(
            @CommandOption(description = "Visible by all", required = false) Boolean ephemeral) {
        interaction.getJDA().getRestPing().submit().thenAccept(x -> {
            interaction.reply("Discord: %d ms".formatted(x)).setEphemeral(ephemeral == null ? false : ephemeral)
                    .submit();
        });
    }

    @CommandDescription("Shutdown the bot")
    public void shutdown() {
        interaction.reply("Bye! :wave:").queue(x -> {
            bot.logout();
        });
    }

    @CommandDescription("Set context value ")
    public void context(
            @CommandOption(description = "Context type", autocompleter = GuildContextTypeAutoCompleter.class) String type,
            @CommandOption(description = "Context label") String label,
            @CommandOption(description = "Context value") String value) {
        GuildContextType<?> contextType = GuildContextType.byName(type);
        Long valueLong = Long.parseLong(value);
        GuildContext context = bot.getContext(interaction.getGuild());
        contextType.map(context.getConfiguration()).put(label, valueLong);
        bot.get(GuildContextService.class).update(context);
        interaction.reply("Done !").queue();
    }

}
