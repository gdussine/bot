package bot.context;

public class GuildContextException extends Exception {

    public GuildContextException(String message) {
        super(message);
    }
    
    public static GuildContextException unknownKey(String keyName) {
        return new GuildContextException("GuildContextKey __%s__ is unknown".formatted(keyName));
    }

}
