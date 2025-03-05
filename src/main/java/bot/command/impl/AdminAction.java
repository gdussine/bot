package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@CommandModule(name = "admin", permission = { Permission.ADMINISTRATOR })
public class AdminAction extends CommandAction {

    @CommandDescription("PING request to Discord API")
    public void ping(
            @CommandOption(name = "ephemeral", description = "Visible by all", required = false) Boolean ephemeral) {
        interaction.getJDA().getRestPing().submit().thenAccept(x -> {
            interaction.reply("Discord: %d ms".formatted(x)).setEphemeral(ephemeral == null ? false : ephemeral)
                    .submit();
        });
    }

    @CommandDescription("Clean amount of message from channel")
    public void clean(@CommandOption(name = "amount", description = "Amount of message") Integer amount) {
        TextChannel channel = interaction.getChannel().asTextChannel();
        channel.getHistory().retrievePast(amount).submit().thenAccept(msgs -> channel.purgeMessages(msgs));
    }

}
