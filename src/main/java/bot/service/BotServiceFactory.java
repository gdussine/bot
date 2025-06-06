package bot.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import bot.core.BotImpl;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class BotServiceFactory {

	private Map<Class<? extends BotService>, BotService> services = new HashMap<>();

	private BotImpl bot;

	public BotServiceFactory(BotImpl bot) {
		this.bot = bot;
	}

	public void createAll() {
		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
			for (ClassInfo classInfo : result.getSubclasses(BotService.class)) {
				if (classInfo.isAbstract())
					continue;
				if (!classInfo.hasAnnotation(BotServiceInfo.class))
					continue;
				Class<? extends BotService> clazz = classInfo.loadClass(BotService.class);
				this.create(clazz);
			}
		}
	}

	public <T extends BotService> void create(Class<T> clazz) {
		try {
			BotService service = clazz.getConstructor().newInstance();
			service.setBot(bot);
			services.put(service.getClass(), service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T extends BotService> T get(Class<T> serviceClass) {
		return serviceClass.cast(services.get(serviceClass));
	}

	public Collection<BotService> getAll() {
		return services.values();
	}

	public void startAll() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(services.size());
		for (BotService service : getAll()) {
			executor.submit(() -> service.run());
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
	}

	public void stopAll() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(services.size());
		for(BotService service : getAll()){
			executor.submit(()->service.shutdown());
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
	}
}
