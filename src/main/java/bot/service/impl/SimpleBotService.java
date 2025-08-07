package bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.api.Bot;
import bot.api.BotService;
import bot.service.BotServiceHandler;

public abstract class SimpleBotService implements BotService {

	protected BotServiceHandler handler;
	protected Logger logger;
	protected Bot bot;

	public SimpleBotService() {
		handler = new BotServiceHandler(this);
		this.logger = LoggerFactory.getLogger(this.getName());
	}

	public Bot getBot() {
		return bot;
	}


	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public final void run() {
		handler.run();
	}

	public final void shutdown() {
		handler.shutdown();
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {
	}

	public BotServiceHandler getHandler() {
		return handler;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
