package bot.command.model;

import java.lang.reflect.Parameter;

import bot.command.annotations.CommandOption;
import bot.command.core.CommandAutoCompleter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandOptionInfo {
    public String name;
    public String description;
    public boolean required = true;
    public CommandAutoCompleter autocompleter;
    public CommandOptionTypes type;
    

    public CommandOptionInfo(String name, String description, boolean required,
            Class<? extends CommandAutoCompleter> autocompleterClass,
            Class<?> type) {
        this.name = name;
        this.description = description;
        this.required = required;
        this.setType(type);
        this.setAutocompleter(autocompleterClass);
    }

    public CommandOptionInfo(Parameter parameter, CommandOption option){
        this(parameter.getName(), option.description(), option.required(), option.autocompleter(), parameter.getType());
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

    public CommandAutoCompleter getAutocompleter() {
        return autocompleter;
    }

    public void setAutocompleter(CommandAutoCompleter autocompleter) {
        this.autocompleter = autocompleter;
    }

    public void setAutocompleter(Class<? extends CommandAutoCompleter> autocompleterClass) {
        try {
            this.autocompleter = autocompleterClass.getConstructor().newInstance();
        } catch (InstantiationException e) {
            this.autocompleter = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandOptionTypes getType() {
        return type;
    }

    public void setType(Class<?> typeClass) {
        this.type = CommandOptionTypes.byClass(typeClass);
    }

    public void setType(CommandOptionTypes type){
        this.type = type;
    }

    public OptionData toOptionData() {
        return new OptionData(type.getType(), name, description, required, autocompleter != null);
    }

    
    public Object getCommandParameter(SlashCommandInteractionEvent event) {
        return event.getOption(name, null, type.getMapper());
    }

}
