package bot.command.core;

import bot.context.GuildContext;
import bot.core.Bot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

public abstract class CommandAction {

    protected SlashCommandInteractionEvent interaction;
    protected Bot bot;
    protected GuildContext context;

    public SlashCommandInteractionEvent getInteraction() {
        return interaction;
    }

    public void hydrate(Bot bot, SlashCommandInteractionEvent interaction){
        this.bot = bot;
        this.interaction = interaction;
        this.context = bot.getContext(interaction.getGuild());

    }

    public ReplyCallbackAction replyException(Exception exception){
        return interaction.reply(exception.getMessage()).setEphemeral(true);
    }


    public ReplyCallbackAction replyDefault(){
        return interaction.reply("Done !").setEphemeral(true);
    }

    public void check() throws CommandException {

    }
}
