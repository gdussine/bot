package bot.command.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class CommandMap {

    private Map<String, CommandEntry> map = new HashMap<>();

    public CommandMap() {

    }

    public void put(CommandEntry entry) {
        map.put(entry.getKey(), entry);
    }

    public CommandEntry get(String module, String name) {
        return map.get(module + "#" + name);
    }

    public List<CommandEntry> getModule(String module) {
        return map.entrySet().stream().filter(x -> x.getKey().startsWith(module + "#")).map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    public SlashCommandData getCommand(String module) {
        SlashCommandData command = Commands.slash(module, "Module " + module);
        getModule(module).forEach(x -> {
            command.setDefaultPermissions(DefaultMemberPermissions.enabledFor(x.getModule().permission()));
            command.addSubcommands(x.toSubcommandData());
        });
        return command;
    }

    public Set<SlashCommandData> getCommands() {
        return map.keySet().stream().map(x -> x.split("#")[0]).distinct().map(x -> getCommand(x))
                .collect(Collectors.toSet());
    }

    public static CommandMap create(String commandPackage) {
        try {
            Set<Class<?>> classSet = ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName().equalsIgnoreCase(commandPackage))
                    .map(clazz -> clazz.load()).filter(clazz -> clazz.isAnnotationPresent(CommandModule.class))
                    .collect(Collectors.toSet());
            CommandMap map = new CommandMap();
            classSet.forEach(commandClass -> {
                CommandModule entity = commandClass.getAnnotation(CommandModule.class);
                for (Method method : commandClass.getDeclaredMethods()) {
                    if (!method.isAnnotationPresent(CommandDescription.class))
                        continue;
                    CommandDescription commandMethod = method.getAnnotation(CommandDescription.class);
                    CommandEntry entry = new CommandEntry(entity, commandMethod, commandClass, method);
                    for (Parameter parameter : method.getParameters()) {
                        if (!parameter.isAnnotationPresent(CommandOption.class))
                            continue;
                        CommandOption commandOption = parameter.getAnnotation(CommandOption.class);
                        entry.addOption(commandOption, parameter);
                    }
                    map.put(entry);
                }
            });
            return map;
        } catch (Exception e) {
            return null;
        }
    }

}
