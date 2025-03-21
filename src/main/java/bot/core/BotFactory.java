package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.command.annotations.CommandModule;
import bot.command.core.CommandAction;
import bot.command.model.CommandDictionnary;
import bot.context.GuildContextService;
import bot.service.core.BotService;
import bot.service.core.BotServices;
import bot.service.core.GenericBotService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.dv8tion.jda.api.JDABuilder;

public class BotFactory {

    protected Logger log;
    protected ClassGraph botGraph;

    public BotFactory(){
        this("bot.*");
    }

    public BotFactory(String...packages) {
        this.botGraph = new ClassGraph().acceptPackages(packages);
        this.log = LoggerFactory.getLogger(getClass());
    }

    protected Bot newBot(){
        CommandDictionnary commands = new CommandDictionnary();
        BotServices services = new BotServices();
        JDABuilder jdaBuilder = JDABuilder.createDefault("");
        return new Bot(jdaBuilder, services, commands);
    }

    public Bot iniBot(){
        Bot bot = this.newBot();
        this.initCommands(bot.getCommands());
        this.initService(bot.services(), bot);
        bot.setContextSupplier(bot.services().get(GuildContextService.class));
        return bot;
    }

    protected CommandDictionnary initCommands(CommandDictionnary commands) {
        try (ScanResult result = botGraph.enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(CommandAction.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(CommandModule.class))
                    continue;
                Class<CommandAction> clazz = classInfo.loadClass(CommandAction.class);
                commands.put(clazz);
                this.log.info("CommandModule {} loaded.", clazz.getSimpleName());
            }
        }
        return commands;
    }

    protected BotServices initService(BotServices services, Bot bot) {
        try (ScanResult result = botGraph.enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(GenericBotService.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(BotService.class))
                    continue;
                Class<? extends GenericBotService> clazz = classInfo.loadClass(GenericBotService.class);
                BotService annotation = clazz.getAnnotation(BotService.class);
                try {
                    GenericBotService service = clazz.getConstructor().newInstance();
                    service.setListener(annotation.listener());
                    service.connect(bot);
                    services.put(service);
                    this.log.info("BotService {} loaded.", clazz.getSimpleName());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return services;
    }


}
