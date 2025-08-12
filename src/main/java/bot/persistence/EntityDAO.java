package bot.persistence;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import jakarta.persistence.EntityManager;

public abstract class EntityDAO<T> {

    protected EntityService service;
    protected Class<T> type;

    public EntityDAO(Class<T> type) {
        this.type = type;
    }

    public void setService(EntityService service) {
        this.service = service;
    }

    public EntityQueryBuilder<T> createEntityQueryBuilder(EntityManager em) {
        return new EntityQueryBuilder<>(em, type);
    }

    public <X> X withTransaction(Function<EntityManager, X> func) {
        return this.service.withTransaction(func);
    }

    public <X> X withoutTransaction(Function<EntityManager, X> func) {
        return this.service.withoutTransaction(func);
    }

    public Optional<T> find(Object id) {
        return withoutTransaction(em -> Optional.ofNullable(em.find(type, id)));
    }

    public List<T> all() {
        return withoutTransaction(em -> new EntityQueryBuilder<>(em, type).build().getResultList());
    }

    public T merge(T t) {
        return withTransaction(em -> em.merge(t));
    }

    public T persist(T t) {
        return withTransaction(em -> {
            em.persist(t);
            return t;
        });
    }

    public Optional<T> delete(Object id){
        return this.withTransaction(em -> {
            Optional<T> optional = Optional.ofNullable(em.find(type, id));
            optional.ifPresent(entity -> em.remove(entity));
            return optional;
        });
    }
}
