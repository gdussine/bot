package bot.service;

import bot.api.Bot;
import bot.core.BotLaunchable;
import bot.persistence.EntityFacade;
import bot.persistence.EntityService;

public abstract class BotService extends BotLaunchable {

	protected Bot bot;
	protected BotListener listener;

	public Bot getBot() {
		return bot;
	}

	public BotListener getListener() {
		return listener;
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public <T extends BotService> T getRunningService(Class<T> type) {
		T service = bot.getService(type);
		service.awaitRunning().join();
		return service;
	}

	public <T> EntityFacade<T> entity(Class<T> type) {
		return getRunningService(EntityService.class).genericEntity(type);
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public void setListener(BotListener listener) {
		this.listener = listener;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {
	}

}
