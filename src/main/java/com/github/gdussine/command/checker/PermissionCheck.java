package com.github.gdussine.command.checker;

import com.github.gdussine.command.exception.CommandPermissionException;
import com.github.gdussine.command.model.Command;
import net.dv8tion.jda.api.Permission;

import java.util.function.Predicate;

public enum PermissionCheck {

    NO_PERMS(x->x.getSrc().getMember().hasPermission(Permission.EMPTY_PERMISSIONS));

    private Predicate<Command> predicate;

    private PermissionCheck(Predicate<Command> predicate){
        this.predicate = predicate;
    }

    public Predicate<Command> getPredicate() {
        return predicate;
    }

    public static void checkCommand(PermissionCheck check, Command command) throws CommandPermissionException {
        if(!check.predicate.test(command))
            throw new CommandPermissionException(command);
    }
}
