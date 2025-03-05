package bot.command.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bot.command.core.CommandAutoCompleters;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CommandOption {
    public String name();
    public String description();
    public boolean required() default true;
    public CommandAutoCompleters autocomplete() default CommandAutoCompleters.NONE;

}
