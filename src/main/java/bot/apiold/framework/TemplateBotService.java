package bot.apiold.framework;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.service.BotServiceHandler;
import io.github.gdussine.bot.api.Bot;
import io.github.gdussine.bot.api.BotService;

public abstract class TemplateBotService implements BotService {

	protected BotServiceHandler handler;
	protected Logger logger;
	protected Bot bot;

	public TemplateBotService() {
		handler = new BotServiceHandler(this);
		this.logger = LoggerFactory.getLogger(this.getName());
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	@Override
	public final void start() {
		handler.run();
	}

	@Override
	public final void stop() {
		handler.shutdown();
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public CompletableFuture<Void> awaitStart() {
		return handler.awaitRunning();
	}

	@Override
	public CompletableFuture<Void> awaitStop() {
		return handler.awaitShuttingDown();
	}

	public BotServiceHandler getHandler() {
		return handler;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
