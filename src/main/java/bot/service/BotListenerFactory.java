package bot.service;

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
			Class<? extends BotListener> botListenerType  = service.getClass().getAnnotation(BotServiceInfo.class).listener();
			if (botListenerType.equals(BotListener.class))
				return null;
			BotListener listener = botListenerType.getConstructor().newInstance();
			listener.setService(service);
			service.setListener(listener);
			bot.getJdaBuilder().addEventListeners(listener);
			listeners.put(botListenerType, listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Collection<BotListener> getAll(){
		return listeners.values();
	}

}
