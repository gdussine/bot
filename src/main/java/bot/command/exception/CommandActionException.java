package bot.command.exception;

import bot.command.core.CommandAction;

public class CommandActionException extends CommandException {


    private CommandAction action;

    public CommandActionException(String reason, CommandAction action) {
        super(reason, action.getInfo());
        this.action = action;

    }

    public static CommandActionException notOwner(CommandAction action) {
        return new CommandActionException("User is not the bot owner", action);
    }

    public CommandAction getAction() {
        return action;
    }
}
