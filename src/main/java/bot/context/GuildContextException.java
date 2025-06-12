package bot.context;

public class GuildContextException extends Exception{

    public GuildContextException(String contextKey){
        super("GuildContext key %s not found".formatted(contextKey));
    }

}
