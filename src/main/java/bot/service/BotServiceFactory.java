package bot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			BotServiceInfo info = clazz.getAnnotation(BotServiceInfo.class);
			BotService service = clazz.getConstructor().newInstance();
			service.setBot(bot);
			BotListener listener = info.listener().getConstructor().newInstance();
			if (listener != null) {
				listener.setService(service);
				service.setListener(listener);
			}
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
		List<Thread> startThreads = new ArrayList<>();
		for(BotService service : getAll()){
			Thread thread = new Thread(() -> service.run(), "BOT %s-StartThread".formatted(service.getName()));
			startThreads.add(thread);
			thread.start();
		}
		for(Thread thread : startThreads){
			thread.join();
		}
	}

	public void stopAll() throws InterruptedException {
		List<Thread> stopThreads = new ArrayList<>();
		for(BotService service : getAll()){
			Thread thread = new Thread(() -> service.shutdown(), "BOT %s-StopThread".formatted(service.getName()));
			stopThreads.add(thread);
			thread.start();
		}
		for(Thread thread : stopThreads){
			thread.join();
		}
	}
}
