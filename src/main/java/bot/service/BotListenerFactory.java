package bot.service;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bot.core.BotImpl;

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
		bot.getServices().forEach(service ->{
			this.create(service);
		});
	}

	public BotListener create(BotService service) {
		try {
			Class<? extends BotListener> type  = service.getClass().getAnnotation(BotServiceInfo.class).listener();
			if (Modifier.isAbstract(type.getModifiers()))
				return null;
			BotListener listener = type.getConstructor().newInstance();
			listener.setService(service);
			service.setListener(listener);
			bot.getJdaBuilder().addEventListeners(listener);
			listeners.put(type, listener);
		} catch (Exception e) {
			service.getLog().error("Listener creation failed", e);
		}
		return null;
	}
	
	public Collection<BotListener> getAll(){
		return listeners.values();
	}

}
