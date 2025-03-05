package bot.context;

import net.dv8tion.jda.api.entities.Guild;

public interface GuildContextSupplier {

    public GuildContext getContext(long guildId);

    public GuildContext getContext(Guild guild);


}
