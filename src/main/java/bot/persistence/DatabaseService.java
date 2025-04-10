package bot.persistence;

import bot.core.Bot;
import bot.service.core.AbstractBotService;
import bot.service.core.BotService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceConfiguration;

@BotService
public class DatabaseService extends AbstractBotService {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private PersistenceConfiguration configuration;

    public DatabaseService() {
        this.configuration = new PersistenceConfiguration("bot.persistence")
                .provider("org.hibernate.jpa.HibernatePersistenceProvider")
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "update");
        try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getClassesWithAnnotation(Entity.class)) {
                configuration.managedClass(classInfo.loadClass());
            }
        }

    }

    public void inTransaction(Runnable runnable){
        EntityTransaction t = this.entityManager.getTransaction();
        t.begin();
        runnable.run();
        t.commit();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    @Override
    public void connect(Bot bot) {
        configuration.property(PersistenceConfiguration.JDBC_USER, bot.getConfiguration().getDatabaseUser())
                .property(PersistenceConfiguration.JDBC_PASSWORD, bot.getConfiguration().getDatabasePassword())
                .property(PersistenceConfiguration.JDBC_DRIVER, bot.getConfiguration().getDatabaseDriver())
                .property(PersistenceConfiguration.JDBC_URL, bot.getConfiguration().getDatabaseUrl());
        this.entityManagerFactory = Persistence.createEntityManagerFactory(configuration);
        this.entityManager = entityManagerFactory.createEntityManager();
        super.connect(bot);
    }

    @Override
    public void disconnect() {
        this.entityManager.close();
        this.entityManagerFactory.close();
        super.disconnect();
    }

}
