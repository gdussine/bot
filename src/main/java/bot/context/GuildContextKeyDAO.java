package bot.context;

import java.util.ArrayList;
import java.util.List;

import bot.persistence.EntityDAO;

public class GuildContextKeyDAO extends EntityDAO<GuildContextKey>{

    public GuildContextKeyDAO() {
        super(GuildContextKey.class);
    }

    public List<GuildContextKey> updateKeys(List<GuildContextKey> providerKeys){
        return service.withTransaction(em ->{
            List<GuildContextKey> resultKeys = new ArrayList<>();
            providerKeys.forEach(key -> resultKeys.add(em.merge(key)));
            return resultKeys;
        });
    }



}
