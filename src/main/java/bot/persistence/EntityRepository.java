package bot.persistence;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import bot.service.BotService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EntityRepository<T> {

    private Class<T> clazz;
    private EntityManager entityManager;

    public EntityRepository(Class<T> clazz, BotService service) {
        this.clazz = clazz;
        this.entityManager = service.getService(DatabaseService.class).getEntityManager();

    }
    protected CriteriaQuery<T> buildQuery(BiFunction<CriteriaBuilder,CriteriaQuery<T>, CriteriaQuery<T>> fn){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        return fn.apply(builder, query);
    }

    public List<T> all(){
        CriteriaQuery<T> criteria = this.buildQuery(((builder, crit) ->{
            return crit.select(crit.from(clazz));
        }));
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<T> list(BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicateFunct) {
        CriteriaQuery<T> criteria = this.buildQuery(((builder, crit) ->{
            Root<T> root = crit.from(clazz);
            return crit.select(root).where(builder.and(predicateFunct.apply(root, builder)));
        }));
        return entityManager.createQuery(criteria).getResultList();
    }
    
    public T one(BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicateFunct) {
    	CriteriaQuery<T> criteria = this.buildQuery(((builder, crit) ->{
            Root<T> root = crit.from(clazz);
            return crit.select(root).where(builder.and(predicateFunct.apply(root, builder)));
        }));
    	return entityManager.createQuery(criteria).getSingleResultOrNull();
    }


    public T one(Object id){
        return this.one((root, cb) ->{
            return List.of(cb.equal(root.get("id"), id));
        });
    }

    public T persist(T object){
        inTransaction(em->em.persist(object));
        return object;
    }

    public T merge(T object){
        inTransaction(em ->em.merge(object));
        return object;
    }

    public T delete(T object){
        inTransaction(em -> em.remove(object));
        return object;
    }

    public List<T> delete(List<T> objects){
        inTransaction((em)->objects.forEach(object ->em.remove(object)));
        return objects;
    }

    public void inTransaction(Consumer<EntityManager> action){
        entityManager.getTransaction().begin();
        action.accept(entityManager);
        entityManager.getTransaction().commit();

    }
}
