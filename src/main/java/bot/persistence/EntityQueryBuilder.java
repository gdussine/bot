package bot.persistence;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EntityQueryBuilder<T> {

    private CriteriaBuilder builder;
    private CriteriaQuery<T> query;
    private Root<T> root;
    private EntityManager em;

    public EntityQueryBuilder(EntityManager em, Class<T> type) {
        this.em = em;
        this.builder = em.getCriteriaBuilder();
        this.query = builder.createQuery(type);
        this.root = query.from(type);
        query.select(root);
    }

    public EntityQueryBuilder<T> where(Predicate predicate){
        query.where(predicate);
        return this;
    }

    public EntityQueryBuilder<T> where(String field, Object object){
        return this.where(builder.equal(root.get(field), object));
    }

    public EntityQueryBuilder<T> orderBy(Order order){
        query.orderBy(order);
        return this;
    }

    public EntityQueryBuilder<T> orderBy(String field, boolean asc){
            return this.orderBy(asc ? builder.asc(root.get(field)) : builder.desc(root.get(field)));
    }

    public TypedQuery<T> build(){
        return em.createQuery(query);
    }

}
