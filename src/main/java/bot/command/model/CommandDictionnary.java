package bot.command.model;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import bot.command.core.CommandAction;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class CommandDictionnary {

    private Map<String, CommandInfo> map = new HashMap<>();

    public CommandDictionnary() {

    }

    public CommandInfo get(String module, String name) {
        return map.get(module + "#" + name);
    }

    public List<CommandInfo> getModule(String module) {
        return map.entrySet().stream().filter(x -> x.getKey().startsWith(module + "#")).map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    public SlashCommandData getData(String module) {
        SlashCommandData command = Commands.slash(module, "Module " + module);
        getModule(module).forEach(x -> {
            command.setDefaultPermissions(DefaultMemberPermissions.enabledFor(x.getPermissions()));
            command.addSubcommands(x.toSubcommandData());
        });
        return command;
    }

    public Set<SlashCommandData> getDataSet() {
        return map.keySet().stream().map(x -> x.split("#")[0]).distinct().map(x -> getData(x))
                .collect(Collectors.toSet());
    }

    public void put(Class<? extends CommandAction> classCommand) {
        CommandModule module = classCommand.getAnnotation(CommandModule.class);
        for (Method method : classCommand.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(CommandDescription.class))
                continue;
            CommandDescription description = method.getAnnotation(CommandDescription.class);
            List<CommandOptionInfo> options = new ArrayList<>();
            CommandInfo info = new CommandInfo(module, description, method, options);
            for (Parameter parameter : method.getParameters()) {
                if (!parameter.isAnnotationPresent(CommandOption.class))
                    continue;
                CommandOption commandOption = parameter.getAnnotation(CommandOption.class);
                options.add(new CommandOptionInfo(info, parameter, commandOption));
            }
            map.put(info.getId(), info);
        }
    }

}
