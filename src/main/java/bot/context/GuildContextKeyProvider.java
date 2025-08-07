package bot.context;

import java.util.List;

public interface GuildContextKeyProvider {

    public List<GuildContextKey> provide();
}
