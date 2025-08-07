package bot.command.impl;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import bot.command.exception.CommandActionException;
import bot.view.BotView;
import bot.view.impl.SystemView;
import net.dv8tion.jda.api.Permission;

@CommandModule(name = "system", permission = { Permission.ADMINISTRATOR })
public class SystemAction extends CommandAction {

    @Override
    public void check() throws CommandActionException {
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

    @CommandDescription("Show service status")
    public void services() {
        BotView view = new SystemView().toServicesView(bot.getServices());
        interaction.replyEmbeds(view.render()).submit();
    }

    @CommandDescription("Shutdown the bot")
    public void shutdown() {
        interaction.replyEmbeds(new SystemView().toShutdownView().render()).queue(x -> {
            bot.shutdown();
        });
    }
}
