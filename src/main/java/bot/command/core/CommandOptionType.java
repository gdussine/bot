package bot.command.core;

import java.util.function.Function;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public enum CommandOptionType {

    STRING(OptionType.STRING, String.class, m -> m.getAsString()),
    USER(OptionType.USER, User.class, m ->m.getAsUser()),
    BOOLEAN(OptionType.BOOLEAN, Boolean.class, m->m.getAsBoolean()),
    ROLE(OptionType.ROLE, Role.class, m->m.getAsRole()),
    TEXTCHANNEl(OptionType.CHANNEL, TextChannel.class, m->m.getAsChannel().asTextChannel()),
    INTEGER(OptionType.INTEGER, Integer.class, m->m.getAsInt());

    private OptionType option;
    private Class<?> clazz;
    private Function<? super OptionMapping, ?> mapper;

    private<T> CommandOptionType(OptionType option, Class<T> clazz, Function<? super OptionMapping, T> mapper) {
        this.option = option;
        this.clazz = clazz;
        this.mapper = mapper;
    }

    public OptionType getOption() {
        return option;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Function<? super OptionMapping, ?> getMapper() {
        return mapper;
    }

    public static CommandOptionType byOptionType(OptionType option) {
        for (CommandOptionType type : CommandOptionType.values()) {
            if (type.option.equals(option))
                return type;

        }
        return null;
    }

    public static CommandOptionType byClass(Class<?> clazz) {
        for (CommandOptionType type : CommandOptionType.values()) {
            if (type.clazz.equals(clazz))
                return type;
        }
        return null;
    }

}
