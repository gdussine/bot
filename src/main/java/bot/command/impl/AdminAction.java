package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.command.core.NumberAutoCompleter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

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

    @CommandDescription("Clean amount of message from channel")
    public void clean(@CommandOption(description = "Amount of message", autocompleter = NumberAutoCompleter.class) Integer amount) {
        TextChannel channel = interaction.getChannel().asTextChannel();
        channel.getHistory().retrievePast(amount).submit()
            .thenAccept(msgs -> channel.purgeMessages(msgs))
            .thenAccept(v -> interaction.reply("Done !").setEphemeral(true).submit());
    }

}
