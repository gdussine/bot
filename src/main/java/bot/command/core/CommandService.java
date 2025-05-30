package bot.command.core;

import bot.command.annotations.CommandModule;
import bot.command.model.CommandDictionnary;
import bot.command.model.CommandInfo;
import bot.service.BotService;
import bot.service.BotServiceInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

@BotServiceInfo(listener = CommandListener.class)
public class CommandService extends BotService {

    private CommandDictionnary commands;

    @Override
    public void start(){
        this.commands = new CommandDictionnary();
        try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(CommandAction.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(CommandModule.class))
                    continue;
                Class<CommandAction> clazz = classInfo.loadClass(CommandAction.class);
                commands.put(clazz);
                this.log.info("Listen {}.", clazz.getSimpleName());
            }
        }
    }

    public void init(Guild guild) {
        guild.updateCommands().addCommands(commands.getDataSet()).queue();
    }

    public void autocomplete(CommandAutoCompleteInteraction interaction) {
        CommandInfo info = commands.get(interaction.getName(), interaction.getSubcommandName());
        info.getOptions().stream().filter(x -> x.getName().equals(interaction.getFocusedOption().getName()))
                .forEach(option -> option.getAutocompleter().accept(this.getBot(), interaction));
    }

    public void execute(SlashCommandInteractionEvent event) {
        CommandInfo info = commands.get(event.getName(), event.getSubcommandName());
        CommandAction action = info.getCommandAction();
        Object[] parameters = info.getCommandParameters(event);
        try {
            action.hydrate(this.getBot(), event);
            action.check();
            info.getMethod().invoke(action, parameters);
        } catch (CommandException e) {
            action.replyException(e).queue();
            this.log.warn(e.getMessage());
        } catch (Exception e) {
            action.replyException(e).queue();
            this.log.error(e.getMessage(), e);
        }
    }

    public CommandDictionnary getCommands() {
        return commands;
    }

}
