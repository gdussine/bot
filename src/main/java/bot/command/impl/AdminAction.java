package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
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

}
