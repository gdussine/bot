package bot.command.model;

import java.lang.reflect.Method;
import java.util.List;

import bot.command.core.CommandAction;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class CommandInfo {

    private String moduleName;
    private Permission[] permissions;
    private String commandDescription;
    private Class<?> type;
    private Method method;
    private List<CommandOptionInfo> options;

    public String getId(){
        return "%s#%s".formatted(moduleName, method.getName());
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

    public void setPermissions(Permission[] permissions) {
        this.permissions = permissions;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public void setCommandDescription(String commandDescription) {
        this.commandDescription = commandDescription;
    }

    public List<CommandOptionInfo> getOptions() {
        return options;
    }

    public void setOptions(List<CommandOptionInfo> options) {
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

    public SubcommandData toSubcommandData() {
        SubcommandData subcommand = new SubcommandData(method.getName(), commandDescription);
        subcommand.addOptions(options.stream().map(option -> option.toOptionData()).toList());
        return subcommand;
    }

    public Object[] getCommandParameters(SlashCommandInteractionEvent event) {
        Object[] result = new Object[options.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.options.get(i).getCommandParameter(event);
        }
        return result;
    }

    public CommandAction getCommandAction() {
        try {
            return CommandAction.class.cast(type.getConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
