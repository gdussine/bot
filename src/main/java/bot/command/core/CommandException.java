package bot.command.core;

public class CommandException extends Exception {

    private CommandAction action;

    public CommandException(String message, CommandAction action) {
        super(message);
        this.action = action;
    }

    public CommandAction getAction() {
        return action;
    }

    public static CommandException notOwner(CommandAction action){
        String message = "You must be the bot owner to execute command %s".formatted(action.getInteraction().getSubcommandName());
        return new CommandException(message, action);
    }

}
