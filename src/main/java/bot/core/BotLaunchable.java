package bot.core;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BotLaunchable {

	protected Logger log;
	protected AtomicReference<BotLaunchableStatus> synchronizedStatus;
	protected ConcurrentHashMap<BotLaunchableStatus, CompletableFuture<Void>> waiters = new ConcurrentHashMap<>();

	public BotLaunchable() {
		this.log = LoggerFactory.getLogger(this.getClass());
		this.synchronizedStatus = new AtomicReference<>(BotLaunchableStatus.CREATED);
	}

	private void setStatus(BotLaunchableStatus status) {
		this.synchronizedStatus.set(status);
		CompletableFuture<Void> waiter = waiters.get(status);
		if (waiter != null) {
			waiter.complete(null);
		}
	}

	public BotLaunchableStatus getStatus() {
		return this.synchronizedStatus.get();
	}

	public void run() {
		try {
			this.start();
			this.setStatus(BotLaunchableStatus.RUNNING);
			log.info("Started.");
		} catch (Exception e) {
			log.error("Failed to run", e);
			;
		}
	}

	public void shutdown() {
		try {
			this.stop();
			this.setStatus(BotLaunchableStatus.SHUTDOWN);
			log.info("Stopped.");
		} catch (Exception e) {
			log.error("Failed to shutdown", e);
		}
	}

	private CompletableFuture<Void> awaitStatus(BotLaunchableStatus status) {
		if (synchronizedStatus.get() == status)
			return CompletableFuture.completedFuture(null);
		return waiters.computeIfAbsent(status, s -> {
			return new CompletableFuture<Void>().whenComplete((v, err) -> {
				if (err != null)
					this.log.error("Not %s".formatted(status.name()), err);
			});
		});
	}

	public CompletableFuture<Void> awaitRunning() {
		return this.awaitStatus(BotLaunchableStatus.RUNNING);
	}

	public CompletableFuture<Void> awaitShuttingDown() {
		return this.awaitStatus(BotLaunchableStatus.SHUTDOWN);
	}

	public Logger getLog() {
		return log;
	}

	public abstract void start() throws Exception;

	public abstract void stop() throws Exception;

}
