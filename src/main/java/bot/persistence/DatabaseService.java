package bot.persistence;

import java.util.logging.Level;

import bot.core.BotConfiguration;
import bot.platform.PlatformService;
import bot.service.BotService;
import bot.service.BotServiceInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceConfiguration;

@BotServiceInfo
public class DatabaseService extends BotService {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private PersistenceConfiguration configuration;


	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		try {
			this.awaitRunning();
			return entityManager;
		} catch (InterruptedException e) {
			log.error("EntityManager is not instanciate.", e);
			return null;
		}
	}

	@Override
	public void start() {
		PlatformService platformService = getBot().getService(PlatformService.class);
		try {
			platformService.awaitRunning();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
				this.log.info("Manage {}.", classInfo.getSimpleName());
			}
		}
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		this.entityManagerFactory = Persistence.createEntityManagerFactory(configuration);
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	public void stop() {
		this.entityManager.close();
		this.entityManagerFactory.close();
	}
}
