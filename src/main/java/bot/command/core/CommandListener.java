package bot.command.core;

import bot.service.impl.SimpleBotListener;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CommandListener extends SimpleBotListener{

    public CommandService getCommandService() {
        return bot.getService(CommandService.class);
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        getCommandService().init(event.getGuild());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        getCommandService().execute(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        getCommandService().autocomplete(event);
    }

}
