package bot.command.model;

import java.util.function.Function;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public enum CommandOptionTypes {

    STRING(OptionType.STRING, String.class, OptionMapping::getAsString),
    USER(OptionType.USER, User.class, OptionMapping::getAsUser),
    MEMBER(OptionType.USER, Member.class, OptionMapping::getAsMember),
    BOOLEAN(OptionType.BOOLEAN, Boolean.class, OptionMapping::getAsBoolean),
    ROLE(OptionType.ROLE, Role.class, OptionMapping::getAsRole),
    CHANNEl(OptionType.CHANNEL, Channel.class, OptionMapping::getAsChannel),
    INTEGER(OptionType.INTEGER, Integer.class, OptionMapping::getAsInt);

    

    private OptionType type;
    private Class<?> clazz;
    private Function<OptionMapping, ?> mapper;

    private<T> CommandOptionTypes(OptionType option, Class<T> clazz, Function<OptionMapping, T> mapper) {
        this.type = option;
        this.clazz = clazz;
        this.mapper = mapper;
    }

    public OptionType getType() {
        return type;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Function<OptionMapping, ?> getMapper() {
        return mapper;
    }

    public static CommandOptionTypes byClass(Class<?> clazz) {
        for (CommandOptionTypes type : CommandOptionTypes.values()) {
            if (type.clazz.equals(clazz))
                return type;
        }
        return null;
    }

}
