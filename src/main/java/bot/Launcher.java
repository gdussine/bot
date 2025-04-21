package bot;

import bot.context.GuildContext;
import bot.context.GuildContextIndex;
import bot.context.GuildContextService;
import bot.core.Bot;
import bot.core.BotFactory;

public class Launcher {

    public static void main(String[] args){
        BotFactory factory = new BotFactory("secret/auth.json");
        Bot bot = factory.initBot();
        bot.login();
        GuildContext ctxt = bot.getContext(667885057133772810L);
        //ctxt.getConfiguration().getRoles().put(GuildContextIndex.Role.MEMBER.name(), 87535834896547079L);
        bot.get(GuildContextService.class).update(ctxt);
        System.out.println(ctxt.getRole(GuildContextIndex.ROLE_MEMBER.name()));
    }
}
