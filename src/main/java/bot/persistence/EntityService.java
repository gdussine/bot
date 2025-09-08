	package bot.persistence;

import java.util.function.Function;
import java.util.logging.Level;

import bot.apiold.framework.TemplateBotService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceConfiguration;

public class EntityService extends TemplateBotService {

	private EntityManagerFactory factory;
	private PersistenceConfiguration persistenceConfiguration;

	@Override
	public void onStart() {
		DatabaseConfiguration databaseConfiguration = getBot().getConfiguration(DatabaseConfiguration.class);
		this.persistenceConfiguration = new PersistenceConfiguration("bot.persistence")
				.provider("org.hibernate.jpa.HibernatePersistenceProvider")
				.property("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
				.property("hibernate.hbm2ddl.auto", databaseConfiguration.getMode())
				.property(PersistenceConfiguration.JDBC_USER, databaseConfiguration.getUser())
				.property(PersistenceConfiguration.JDBC_PASSWORD, databaseConfiguration.getPassword())
				.property(PersistenceConfiguration.JDBC_DRIVER, databaseConfiguration.getDriver())
				.property(PersistenceConfiguration.JDBC_URL, databaseConfiguration.getUrl());

		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
			for (ClassInfo classInfo : result.getClassesWithAnnotation(Entity.class)) {
				persistenceConfiguration.managedClass(classInfo.loadClass());
				getLogger().info("Create Table {}.", classInfo.getSimpleName());
			}
		}
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		this.factory = Persistence.createEntityManagerFactory(persistenceConfiguration);
		this.logger.info("Connected to %s as %s".formatted(databaseConfiguration.getUrl(), databaseConfiguration.getUser()));
	}

	public <T extends EntityDAO<?>> T registerDAO(T dao) {
		dao.setService(this);
		return dao;
	}

	public <T> T withTransaction(Function<EntityManager, T> func) {
		try (EntityManager em = factory.createEntityManager()) {
			EntityTransaction tx = em.getTransaction();
			try {
				tx.begin();
				T result = func.apply(em);
				tx.commit();
				return result;
			} catch (Exception e) {
				getLogger().error("Transaction failed", e);
				if (tx.isActive())
					tx.rollback();
				throw e;
			}
		}
	}

	public <T> T withoutTransaction(Function<EntityManager, T> func) {
		try (EntityManager em = factory.createEntityManager()) {
			try {
				return func.apply(em);
			} catch (Exception e) {
				getLogger().error("Transaction failed", e);
				throw e;
			}
		}
	}

	public void onStop() {
		this.factory.close();
	}
}
