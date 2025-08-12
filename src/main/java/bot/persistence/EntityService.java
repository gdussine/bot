package bot.persistence;

import java.util.function.Function;
import java.util.logging.Level;

import bot.api.simple.TemplateBotService;
import bot.core.BotConfiguration;
import bot.platform.PlatformService;
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
	private PersistenceConfiguration configuration;

	@Override
	public void start() {
		PlatformService platformService = getBot().getService(PlatformService.class);
		BotConfiguration botConfig = platformService.getBotConfiguration();
		this.configuration = new PersistenceConfiguration("bot.persistence")
				.provider("org.hibernate.jpa.HibernatePersistenceProvider")
				.property("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
				.property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "update")
				.property(PersistenceConfiguration.JDBC_USER, botConfig.getDatabaseUser())
				.property(PersistenceConfiguration.JDBC_PASSWORD, botConfig.getDatabasePassword())
				.property(PersistenceConfiguration.JDBC_DRIVER, botConfig.getDatabaseDriver())
				.property(PersistenceConfiguration.JDBC_URL, botConfig.getDatabaseUrl());
		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
			for (ClassInfo classInfo : result.getClassesWithAnnotation(Entity.class)) {
				configuration.managedClass(classInfo.loadClass());
				getLogger().info("Create Table {}.", classInfo.getSimpleName());
			}
		}
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		this.factory = Persistence.createEntityManagerFactory(configuration);
	}

	public <T extends EntityDAO<?>> T registerDAO( T dao){
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

	public void stop() {
		this.factory.close();
	}
}
