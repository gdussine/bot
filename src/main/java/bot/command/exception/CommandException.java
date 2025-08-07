package bot.command.exception;

import bot.command.model.CommandInfo;

public class CommandException extends Exception {

    private static final long serialVersionUID = 1L;
    private CommandInfo info;

    public CommandException(String message){
        super(message);
    }

    public CommandException(String message, CommandInfo info){
        this(message);
        this.info = info;
    }

    public CommandInfo getInfo() {
        return info;
    }


    public static CommandException actionCreationException(CommandInfo info){
        String message = "Impossible to create action from info %s %s".formatted(info.getMethod().getName());
        return new CommandException(message, info);
    }

    public static CommandException autoCompleterCreationException(CommandInfo info, String autoComplaterName){
        String message = "Autocompleter %s creation failed".formatted(autoComplaterName);
        return new CommandException(message, info);
    }

}
