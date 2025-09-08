package bot.command.core;

import bot.command.exception.CommandActionException;
import bot.command.model.CommandInfo;
import bot.core.DiscordConfiguration;
import io.github.gdussine.bot.api.Bot;
import io.github.gdussine.bot.api.GuildContext;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

public abstract class CommandAction {

    protected SlashCommandInteractionEvent interaction;
    protected CommandInfo info;
    protected Bot bot;
    protected GuildContext context;

    public SlashCommandInteractionEvent getInteraction() {
        return interaction;
    }

    public CommandInfo getInfo() {
        return info;
    }

    public void hydrate(Bot bot, SlashCommandInteractionEvent interaction) {
        this.bot = bot;
        this.interaction = interaction;
        this.context = bot.getContext(interaction.getGuild());
    }

    public ReplyCallbackAction replyDefault() {
        return interaction.reply("Done !").setEphemeral(true);
    }

    public void check() throws CommandActionException {

    }

    protected void checkOwner() throws CommandActionException {
        if (bot.getConfiguration(DiscordConfiguration.class).getOwnerId().equals(interaction.getUser().getIdLong()))
            return;
        throw CommandActionException.notOwner(this);
    }
}
