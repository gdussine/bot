package bot.command.core;

import bot.service.core.BotListener;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CommandListener extends BotListener{

    @Override
    public CommandService getService() {
        return (CommandService) super.getService();
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        getService().init(event.getGuild());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        getService().execute(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        getService().autocomplete(event);
    }

}
