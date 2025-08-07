package bot.context;

import bot.persistence.EntityDAO;

public class GuildContextValueDAO extends EntityDAO<GuildContextValue>{

    public GuildContextValueDAO() {
        super(GuildContextValue.class);
    }

}
