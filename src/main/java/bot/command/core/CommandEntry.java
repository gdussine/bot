package bot.command.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.model.CommandOptionInfo;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class CommandEntry {

    private CommandModule module;
    private CommandDescription description;
    private Class<?> type;
    private Method method;
    private List<CommandOptionInfo> optionInfo;

    public CommandEntry(CommandModule module, CommandDescription command, Class<?> type, Method method) {
        this.module = module;
        this.description = command;
        this.type = type;
        this.method = method;
        this.optionInfo = new ArrayList<>();
    }

    public void addOption(CommandOption option, Parameter parameter) {
        this.optionInfo.add(new CommandOptionInfo(parameter.getName(),option.description(), option.required(), option.autocompleter(), parameter.getType()));   
    }

    public SubcommandData toSubcommandData() {
        SubcommandData subcommand = new SubcommandData(method.getName(), description.value());
        subcommand.addOptions(optionInfo.stream().map(option -> option.toOptionData()).toList());
        return subcommand;
    }

    public CommandAction getCommandAction() {
        try {
            return CommandAction.class.cast(type.getConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[] getCommandParameters(SlashCommandInteractionEvent event) {
        Object[] result = new Object[optionInfo.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.optionInfo.get(i).getCommandParameter(event);
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

    public List<CommandOptionInfo> getOptionInfo() {
        return optionInfo;
    }

    public void setOptionInfo(List<CommandOptionInfo> optionInfo) {
        this.optionInfo = optionInfo;
    }

    

}
