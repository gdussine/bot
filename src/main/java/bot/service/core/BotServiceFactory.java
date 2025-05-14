package bot.service.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bot.core.Bot;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class BotServiceFactory {

	private Map<Class<? extends BotService>, BotService> services = new HashMap<>();

	private Bot bot;

	public BotServiceFactory(Bot bot) {
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
	
	public void callbackBeforeDiscordLogin() {
		getAll().forEach(service -> service.beforeDiscordLogin());
	}
	
	public void callbackAfterDiscordLogin() {
		getAll().forEach(service -> service.afterDiscordLogin());
	}
	

	public void close() {
		for (BotService service : services.values()) {
			service.disconnect();
		}
	}

}
