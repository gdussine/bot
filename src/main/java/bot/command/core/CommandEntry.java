package bot.command.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandOption;
import bot.core.Bot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class CommandEntry {

    private CommandModule module;
    private CommandDescription description;
    private List<CommandOption> options;

    private Class<?> type;
    private Method method;
    private List<Parameter> parameters;

    public CommandEntry(CommandModule module, CommandDescription command, Class<?> type, Method method) {
        this.module = module;
        this.description = command;
        this.type = type;
        this.method = method;
        this.options = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    public void addOption(CommandOption option, Parameter parameter) {
        this.options.add(option);
        this.parameters.add(parameter);
    }

    public SubcommandData toSubcommandData() {
        SubcommandData subcommand = new SubcommandData(method.getName(), description.value());
        for (int i = 0; i < options.size(); i++) {
            subcommand.addOptions(new OptionData(CommandOptionType.byClass(parameters.get(i).getType()).getOption(),
                    options.get(i).name(), options.get(i).description(),
                    options.get(i).required(), !options.get(i).autocomplete().equals(CommandAutoCompleters.NONE)));
        }
        return subcommand;
    }

    public CommandAction toCommandAction(Bot bot, SlashCommandInteractionEvent event) {
        try {
            CommandAction action = CommandAction.class.cast(type.getConstructor().newInstance());
            action.hydrate(bot, event);
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[] toCommandParameters(SlashCommandInteractionEvent event) {
        Object[] result = new Object[parameters.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = event.getOption(options.get(i).name(), null,
                    CommandOptionType.byClass(parameters.get(i).getType()).getMapper());
        }
        return result;
    }

    public String getKey() {
        return module.name() + "#" + method.getName();
    }

    public CommandModule getModule() {
        return module;
    }

    public void setModule(CommandModule module) {
        this.module = module;
    }

    public CommandDescription getDescription() {
        return description;
    }

    public void setDescription(CommandDescription command) {
        this.description = command;
    }

    public List<CommandOption> getOptions() {
        return options;
    }

    public void setOptions(List<CommandOption> options) {
        this.options = options;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameter) {
        this.parameters = parameter;
    }

}
