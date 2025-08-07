package bot.context;

import java.util.List;

public class MinimalGuildContextKeyProvider implements GuildContextKeyProvider{

    @Override
    public List<GuildContextKey> provide() {
        return List.of(new GuildContextKey(GuildContextType.STRING, "default.license"));
    }



}
