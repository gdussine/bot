package bot.service.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bot.core.Bot;

/**
 * Manage BotListener instanciation
 */
public class BotListenerFactory {

	private Bot bot;
	
	private Map<Class<? extends BotListener>, BotListener> listeners = new HashMap<>();

	public BotListenerFactory(Bot bot) {
		this.bot = bot;
	}
	
	public void createAll() {
		bot.getBotServiceFactory().getAll().forEach(service ->{
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
