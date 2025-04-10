package bot.core;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import bot.command.annotations.CommandModule;
import bot.command.core.CommandAction;
import bot.command.model.CommandDictionnary;
import bot.context.GuildContextService;
import bot.service.core.AbstractBotService;
import bot.service.core.BotService;
import bot.service.core.BotServiceFactory;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.dv8tion.jda.api.JDABuilder;

public class BotFactory {

    protected Logger log;
    protected ClassGraph botGraph;
    protected String configurationPath;
    private ObjectMapper mapper;

    public BotFactory(String configurationPath) {
        this(configurationPath, "bot.*");
    }

    public BotFactory(String configurationPath, String... packages) {
        this.botGraph = new ClassGraph().acceptPackages(packages);
        this.log = LoggerFactory.getLogger(getClass());
        this.configurationPath = configurationPath;
        this.mapper = new ObjectMapper();
    }

    protected Bot newBot() {
        CommandDictionnary commands = new CommandDictionnary();
        BotServiceFactory services = new BotServiceFactory();
        JDABuilder jdaBuilder = JDABuilder.createDefault("");
        BotConfiguration configuration = new BotConfiguration();
        return new Bot(jdaBuilder, configuration, services, commands);
    }

    public Bot initBot() {
        Bot bot = this.newBot();
        this.initConfiguration(bot);
        this.initCommands(bot);
        this.initService(bot);
        bot.setContextSupplier(bot.getBotServiceFactory().get(GuildContextService.class));
        return bot;
    }

    protected BotConfiguration initConfiguration(Bot bot) {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(configurationPath)) {
            bot.getConfiguration().setConfiguration(mapper.readValue(in, BotConfiguration.class));
            return bot.getConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected CommandDictionnary initCommands(Bot bot) {
        try (ScanResult result = botGraph.enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(CommandAction.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(CommandModule.class))
                    continue;
                Class<CommandAction> clazz = classInfo.loadClass(CommandAction.class);
                bot.getCommands().put(clazz);
                this.log.info("CommandModule {} loaded.", clazz.getSimpleName());
            }
        }
        return bot.getCommands();
    }

    protected BotServiceFactory initService(Bot bot) {
        try (ScanResult result = botGraph.enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(AbstractBotService.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(BotService.class))
                    continue;
                Class<? extends AbstractBotService> clazz = classInfo.loadClass(AbstractBotService.class);
                bot.getBotServiceFactory().create(clazz, bot);
            }
        }
        return bot.getBotServiceFactory();
    }

}
