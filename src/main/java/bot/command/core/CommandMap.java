package bot.command.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.annotations.CommandOption;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
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

    public void add(Class<? extends CommandAction> classCommand) {
        CommandModule entity = classCommand.getAnnotation(CommandModule.class);
        for (Method method : classCommand.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(CommandDescription.class))
                continue;
            CommandDescription commandMethod = method.getAnnotation(CommandDescription.class);
            CommandEntry entry = new CommandEntry(entity, commandMethod, classCommand, method);
            for (Parameter parameter : method.getParameters()) {
                if (!parameter.isAnnotationPresent(CommandOption.class))
                    continue;
                CommandOption commandOption = parameter.getAnnotation(CommandOption.class);
                entry.addOption(commandOption, parameter);
            }
            this.put(entry);
        }
    }

    public static CommandMap create(Set<Class<? extends CommandAction>> classSet) {
        CommandMap map = new CommandMap();
        classSet.forEach(commandClass -> {
            map.add(commandClass);
        });
        return map;
    }

    public static CommandMap create(){
        Set<Class<? extends CommandAction>> classSet = new HashSet<>();
        try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo().scan()) {
                        for (ClassInfo classInfo : result.getSubclasses(CommandAction.class)) {
                if (classInfo.isAbstract())
                    continue;
                if(!classInfo.hasAnnotation(CommandModule.class))
                    continue;
                classSet.add(classInfo.loadClass(CommandAction.class));
            }
        }
        return create(classSet);
    }

}
