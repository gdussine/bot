package bot.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bot.api.BotListener;
import bot.core.BotImpl;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

/**
 * Manage BotListener instanciation
 */

public class BotListenerFactory {

	private BotImpl bot;

	private Map<Class<? extends BotListener>, BotListener> listeners = new HashMap<>();

	public BotListenerFactory(BotImpl bot) {
		this.bot = bot;
	}

	public void createAll() {
		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
			for (ClassInfo classInfo : result.getClassesImplementing(BotListener.class)) {
				if (classInfo.isAbstract())
					continue;
				Class<? extends BotListener> clazz = classInfo.loadClass(BotListener.class);
				this.create(clazz);
			}
		}
	}

	public <T extends BotListener> void create(Class<T> clazz) {
		try {
			T listener = clazz.getConstructor().newInstance();
			listener.setBot(bot);
			bot.getJdaBuilder().addEventListeners(listener);
			listeners.put(clazz, listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public Collection<BotListener> getAll() {
		return listeners.values();
	}

}

