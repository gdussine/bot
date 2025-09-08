package bot.context;

import java.util.Optional;

import bot.persistence.EntityDAO;
import jakarta.persistence.EntityManager;

public class GuilContextEntryDAO extends EntityDAO<GuildContextEntry>{

    public GuilContextEntryDAO() {
        super(GuildContextEntry.class);
    }

    public Optional<GuildContextEntry> find(EntityManager em, Long guildId, String key){
        return createEntityQueryBuilder(em).where(eqb ->{
            return eqb.getBuilder().and(
                eqb.getBuilder().equal(eqb.getRoot().get("contextKey"), key),
                eqb.getBuilder().equal(eqb.getRoot().get("guildId"), guildId)  
            );
        }).build().setMaxResults(1).getResultStream().findFirst();
    }

}
