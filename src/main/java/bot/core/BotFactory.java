package bot.core;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import bot.command.annotations.CommandModule;
import bot.command.core.CommandAction;
import bot.command.model.CommandDictionnary;
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


    public BotFactory(String configurationPath, String... packages) {
        this.botGraph = new ClassGraph().acceptPackages(packages);
        this.log = LoggerFactory.getLogger(getClass());
        this.configurationPath = configurationPath;
        this.mapper = new ObjectMapper();
    }

    public BotFactory(String configurationPath) {
        this(configurationPath, "bot.*");
    }

    public Bot createBot() {
        Bot bot = new Bot();
        this.initBot(bot);
        this.initConfiguration(bot);
        this.initCommands(bot);
        this.initService(bot);
        return bot;
    }

    protected void initBot(Bot bot){
        bot.setJdaBuilder(JDABuilder.createDefault(""));
    }

    protected void initConfiguration(Bot bot) {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(configurationPath)) {
            bot.setConfiguration(mapper.readValue(in, BotConfiguration.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initCommands(Bot bot) {
        CommandDictionnary commands = new CommandDictionnary();
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
        bot.setCommands(commands);  
    }

    protected void initService(Bot bot) {
        BotServiceFactory botServiceFactory = new BotServiceFactory();
        try (ScanResult result = botGraph.enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(AbstractBotService.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(BotService.class))
                    continue;
                Class<? extends AbstractBotService> clazz = classInfo.loadClass(AbstractBotService.class);
                botServiceFactory.create(clazz, bot);
            }
        }
        bot.setBotServiceFactory(botServiceFactory);
    }

}
