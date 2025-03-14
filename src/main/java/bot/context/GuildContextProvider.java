package bot.context;

import net.dv8tion.jda.api.entities.Guild;

public interface GuildContextProvider {

    public GuildContext getContext(long guildId);

    public GuildContext getContext(Guild guild);


}
