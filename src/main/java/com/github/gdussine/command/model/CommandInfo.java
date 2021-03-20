package com.github.gdussine.command.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    public String name();
    public String description() default  "";
    public String usage() default "";
    public String category() default "principale";
    public String[] alias() default {};


}
