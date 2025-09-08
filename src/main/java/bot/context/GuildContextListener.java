package bot.context;

import bot.apiold.framework.TemplateBotListener;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;

public class GuildContextListener extends TemplateBotListener {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        bot.getService(GuildContextService.class).initGuild(event);
    }

}
