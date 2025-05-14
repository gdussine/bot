package bot.persistence;

import bot.service.core.BotService;
import bot.service.core.BotServiceInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceConfiguration;

@BotServiceInfo
public class DatabaseService extends BotService {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	private PersistenceConfiguration configuration;

	public DatabaseService() {
		this.configuration = new PersistenceConfiguration("bot.persistence")
				.provider("org.hibernate.jpa.HibernatePersistenceProvider")
				.property("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
				.property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "drop-and-create");
		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
			for (ClassInfo classInfo : result.getClassesWithAnnotation(Entity.class)) {
				configuration.managedClass(classInfo.loadClass());
			}
		}

	}

	public void inTransaction(Runnable runnable) {
		EntityTransaction t = this.entityManager.getTransaction();
		t.begin();
		runnable.run();
		t.commit();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void beforeDiscordLogin() {
		configuration.property(PersistenceConfiguration.JDBC_USER, getBot().getConfiguration().getDatabaseUser())
				.property(PersistenceConfiguration.JDBC_PASSWORD, getBot().getConfiguration().getDatabasePassword())
				.property(PersistenceConfiguration.JDBC_DRIVER, getBot().getConfiguration().getDatabaseDriver())
				.property(PersistenceConfiguration.JDBC_URL, getBot().getConfiguration().getDatabaseUrl());
		this.entityManagerFactory = Persistence.createEntityManagerFactory(configuration);
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void disconnect() {
		this.entityManager.close();
		this.entityManagerFactory.close();
		super.disconnect();
	}

}
