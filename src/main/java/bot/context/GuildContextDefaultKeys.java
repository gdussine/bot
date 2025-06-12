package bot.context;

import java.util.Set;

public class GuildContextDefaultKeys implements GuildContextKeyProvider{

    @Override
    public Set<String> provide() {
        return Set.of("DEFAULT_KEY");
    }

}
