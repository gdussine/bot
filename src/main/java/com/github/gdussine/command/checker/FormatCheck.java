package com.github.gdussine.command.checker;

import com.github.gdussine.command.exception.CommandFormatException;
import com.github.gdussine.command.model.Command;

import java.util.function.Predicate;

public enum FormatCheck {

    NO_ARGS(x->x.getArgs().length == 0),
    ONE_OR_LESS_ARG(x->x.getArgs().length <= 1);

    private Predicate<Command> predicate;

    private FormatCheck(Predicate<Command> predicate){
        this.predicate = predicate;
    }

    public Predicate<Command> getPredicate() {
        return predicate;
    }

    public static void checkCommand(FormatCheck check, Command command) throws CommandFormatException {
        if(!check.predicate.test(command))
            throw new CommandFormatException(command);
    }
}
