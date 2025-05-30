package bot.service;

import bot.api.Bot;
import bot.core.BotLaunchable;

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

	public <T extends BotService> T getService(Class<T> clazz){
		return bot.getService(clazz);
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public void setListener(BotListener listener) {
		this.listener = listener;
	}

	@Override
	public void start() throws InterruptedException {
		
	}

	@Override
	public void stop() throws InterruptedException {
	}

}
