package bot.command.core;

import java.lang.reflect.InvocationTargetException;

import bot.api.framework.TemplateBotService;
import bot.command.annotations.CommandModule;
import bot.command.exception.CommandActionException;
import bot.command.exception.CommandException;
import bot.command.model.CommandDictionnary;
import bot.command.model.CommandInfo;
import bot.command.model.CommandOptionInfo;
import bot.view.ExceptionView;
import bot.view.impl.CommandActionExceptionView;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public class CommandService extends TemplateBotService {

    private CommandDictionnary commands;

    @Override
    public void start() {
        this.commands = new CommandDictionnary();
        try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(CommandAction.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(CommandModule.class))
                    continue;
                Class<CommandAction> clazz = classInfo.loadClass(CommandAction.class);
                commands.put(clazz);
                getLogger().info("Listen {}.", clazz.getSimpleName());
            }
        }
    }

    public void init(Guild guild) {
        guild.updateCommands().addCommands(commands.getDataSet()).queue();
    }

    public void autocomplete(CommandAutoCompleteInteraction interaction) {
        CommandInfo info = commands.get(interaction.getName(), interaction.getSubcommandName());
        CommandOptionInfo option = info.getOptions().stream()
                .filter(x -> x.getName().equals(interaction.getFocusedOption().getName()))
                .findFirst().orElse(null);
        try {
            option.getAutocompleter().accept(bot, interaction);
        } catch (CommandException e) {
            logger.error(e.getMessage());
        }
    }

    public void execute(SlashCommandInteractionEvent event) {
        try {
            CommandInfo info = commands.get(event.getName(), event.getSubcommandName());
            CommandAction action = info.getCommandAction();
            Object[] parameters = info.getCommandParameters(event);
            action.hydrate(this.getBot(), event);
            action.check();
            try {
                info.getMethod().invoke(action, parameters);
                this.getLogger().info("{} executed successfully {} ", event.getUser().getName(), event.getCommandString());
            } catch (InvocationTargetException ite) {
                if (ite.getCause() instanceof Exception)
                    throw (Exception) ite.getCause();
            }
        } catch (CommandActionException e) {
            CommandActionExceptionView view = new CommandActionExceptionView(e);
            event.replyEmbeds(view.render()).setEphemeral(true).submit();
            getLogger().warn("{} failed to execute {} : ", event.getUser().getName(), event.getCommandString(), e.getMessage());
        } catch (Exception e) {
            ExceptionView view = new ExceptionView(e);
            event.replyEmbeds(view.render()).setEphemeral(true).submit();
            getLogger().error("{} failed to execute {} : ", event.getUser().getName(), event.getCommandString(), e.getMessage());
        }
    }

    public CommandDictionnary getCommands() {
        return commands;
    }

}
