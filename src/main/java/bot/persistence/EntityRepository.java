package bot.persistence;

import java.util.List;
import java.util.function.BiFunction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EntityRepository<T> {

    private EntityService entityService;
    private Class<T> type;

    public EntityRepository(EntityService entityService, Class<T> type) {
        this.entityService = entityService;
        this.type = type;
    }

    public T persist(T object) {
        return entityService.withTransaction(em -> {
            em.persist(object);
            return object;
        });
    }

    public T merge(T object) {
        return entityService.withTransaction(em -> {
            return em.merge(object);
        });
    }

    public T delete(T object) {
        return entityService.withTransaction(em -> {
            T target = em.merge(object); 
            em.remove(target);
            return target;
        });
    }

    public List<T> delete(List<T> objects) {
        return entityService.withTransaction((em) -> {
            objects.forEach(object -> em.remove(object));
            return objects;
        });
    }

    protected List<T> query(BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicateFunct){
        return entityService.withTransaction(em ->{
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root).where(builder.and(predicateFunct.apply(root, builder)));
            return em.createQuery(query).getResultList();
        });
    }

    public List<T> all() {
        return query((root, cb) ->{
            return List.of(cb.conjunction());
        });
    }

    public List<T> list(BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicates){
        return query(predicates);
    }

    public T one(BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicates){
        List<T> result = query(predicates);
        if(result.isEmpty())
            return null;
        else
            return result.getFirst();
    }

    public T one(Object id) {
        return entityService.withTransaction(em ->{
            return em.find(type, id);
        });
    }

}

