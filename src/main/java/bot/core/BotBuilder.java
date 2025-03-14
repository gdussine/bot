package bot.core;

import java.util.Arrays;

import bot.command.core.CommandAction;
import bot.command.core.CommandManager;
import bot.command.core.CommandMap;
import bot.context.GuildContextService;
import net.dv8tion.jda.api.JDABuilder;

public class BotBuilder {

    private CommandMap commands;
    private BotServices services;
    private JDABuilder jdaBuilder;

    public BotBuilder(){
        this.commands = new CommandMap();
        this.services = new BotServices();
        this.jdaBuilder = JDABuilder.createDefault("");
        this.registerServices(new CommandManager(), new GuildContextService());
    }

    public BotBuilder registerCommand(Class<? extends CommandAction> commandClass) {
        commands.add(commandClass);
        return this;
    }

    public BotBuilder registerService(BotService service){
        services.set(service);
        return this;
    }

    public BotBuilder registerServices(BotService...serviceList){
        Arrays.stream(serviceList).forEach(service->services.set(service));
        return this;
    }

    public Bot build() {
        Bot bot = new Bot(jdaBuilder, services);
        bot.setContextSupplier(bot.services().get(GuildContextService.class));
        services.connect(bot);
        return bot;
    }

}
