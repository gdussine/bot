package bot.command.core;

import java.util.function.Consumer;

import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

public enum CommandAutoCompleters {

    NONE(null);

    private Consumer<CommandAutoCompleteInteraction> completer;
    
    private CommandAutoCompleters(Consumer<CommandAutoCompleteInteraction> completer ){
            this.completer = completer;
    }

    public Consumer<CommandAutoCompleteInteraction> getCompleter() {
    	
        return completer;
    }

}
