package bot.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.core.Bot;

public abstract class BotService {

	private Bot bot;
	private BotListener listener;
	protected Logger log;

	public BotService() {
		this.log = LoggerFactory.getLogger(getClass());
	}

	public Bot getBot() {
		return bot;
	}

	public BotListener getListener() {
		return listener;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public void setListener(BotListener listener) {
		this.listener = listener;
	}
	
	public void beforeDiscordLogin() {
		
	}
	
	public void afterDiscordLogin() {
		
	}


	public boolean isConnected() {
		return this.bot != null;
	}

	public void disconnect() {
		this.bot = null;
		this.listener = null;
	}

}
