package bot.command.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bot.command.core.CommandEntry;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class CommandDictionnary {

    private Map<String, CommandInfo> map = new HashMap<>();

    public void put(CommandInfo info) {
        map.put(info.getId(), info);
    }

    public CommandInfo get(String module, String name) {
        return map.get(module + "#" + name);
    }

    public List<CommandInfo> getModule(String module) {
        return map.entrySet().stream().filter(x -> x.getKey().startsWith(module + "#")).map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    public SlashCommandData getCommandData(String module) {
        SlashCommandData command = Commands.slash(module, "Module " + module);
        getModule(module).forEach(x -> {
            command.setDefaultPermissions(DefaultMemberPermissions.enabledFor(x.getPermissions()));
            command.addSubcommands(x.toSubcommandData());
        });
        return command;
    }

    public Set<SlashCommandData> getCommands() {
        return map.keySet().stream().map(x -> x.split("#")[0]).distinct().map(x -> getCommandData(x))
                .collect(Collectors.toSet());
    }


}
