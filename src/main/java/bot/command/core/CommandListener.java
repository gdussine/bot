package bot.command.core;

import javax.annotation.Nonnull;

import bot.core.BotListener;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CommandListener extends BotListener{

    @Override
    public void onGuildReady(@Nonnull GuildReadyEvent event) {
        //bot.getCommandManager().init(event.getGuild());
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        //bot.getCommandManager().execute(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        //bot.getCommandManager().autocomplete(event);
    }

}
