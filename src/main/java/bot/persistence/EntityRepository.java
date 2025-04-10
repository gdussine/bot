package bot.persistence;

import java.util.List;
import java.util.function.BiFunction;

import bot.service.core.AbstractBotService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EntityRepository<T> {

    private Class<T> clazz;
    private AbstractBotService service;

    public EntityRepository(Class<T> clazz, AbstractBotService service) {
        this.clazz = clazz;
        this.service = service;
    }

    public EntityManager getEntityManager(){
        return service.getBot().getEntityManager();
    }

    public CriteriaQuery<T> query(BiFunction<CriteriaBuilder,CriteriaQuery<T>, CriteriaQuery<T>> fn){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        return fn.apply(builder, query);
    }

    public List<T> all(){
        CriteriaQuery<T> criteria = this.query(((builder, crit) ->{
            return crit.select(crit.from(clazz));
        }));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<T> list(BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicateFunct) {
        CriteriaQuery<T> criteria = this.query(((builder, crit) ->{
            Root<T> root = crit.from(clazz);
            return crit.select(root).where(predicateFunct.apply(root, builder));
        }));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public T persist(T object){
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(object);
        getEntityManager().getTransaction().commit();
        return object;
    }

    public T merge(T object){
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(object);
        getEntityManager().getTransaction().commit();
        return object;
    }
}
