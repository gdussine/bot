package bot.command.model;

import java.lang.reflect.Parameter;

import bot.command.annotations.CommandOption;
import bot.command.core.CommandAutoCompleter;
import bot.command.exception.CommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandOptionInfo {

    private CommandInfo info;
    private String name;
    private String description;
    private boolean required = true;
    private Class<? extends CommandAutoCompleter> autocompleterClass;
    private CommandOptionTypes type;

    public CommandOptionInfo(CommandInfo info, String name, String description, boolean required,
            Class<? extends CommandAutoCompleter> autocompleterClass,
            CommandOptionTypes type) {
        this.info = info;
        this.name = name;
        this.description = description;
        this.required = required;
        this.autocompleterClass = autocompleterClass;
        this.type = type;
    }

    public CommandOptionInfo(CommandInfo info, Parameter parameter, CommandOption option) {
        this(info, parameter.getName(), option.description(), option.required(), option.autocompleter(),
                CommandOptionTypes.byClass(parameter.getType()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public CommandAutoCompleter getAutocompleter() throws CommandException {
        try {
            CommandAutoCompleter autocompleter = autocompleterClass.getConstructor().newInstance();
            return autocompleter;
        } catch (InstantiationException e) {
            return null;
        } catch (Exception e) {
            throw CommandException.autoCompleterCreationException(info, this.autocompleterClass.getSimpleName());
        }
    }

    public CommandOptionTypes getType() {
        return type;
    }

    public void setType(CommandOptionTypes type) {
        this.type = type;
    }

    public OptionData toOptionData() {
        return new OptionData(type.getType(), name, description, required, !autocompleterClass.equals(CommandAutoCompleter.class));
    }

    public Object getCommandParameter(SlashCommandInteractionEvent event) {
        return event.getOption(name, null, type.getMapper());
    }

    public Class<? extends CommandAutoCompleter> getAutocompleterClass() {
        return autocompleterClass;
    }

    public void setAutocompleterClass(Class<? extends CommandAutoCompleter> autocompleterClass) {
        this.autocompleterClass = autocompleterClass;
    }

    public CommandInfo getInfo() {
        return info;
    }

    public void setInfo(CommandInfo info) {
        this.info = info;
    }

}
