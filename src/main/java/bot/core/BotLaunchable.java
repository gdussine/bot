package bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BotLaunchable {

	protected Logger log;
	protected BotLaunchableStatus status;

	public BotLaunchable() {
		this.log = LoggerFactory.getLogger(this.getClass());
		this.status = BotLaunchableStatus.OFF;
	}

	public void run() {
		BotLaunchableStatus targetStatus = BotLaunchableStatus.ON;
		try {
			this.start();
			synchronized (this) {
				this.status = targetStatus;
				this.notify();
			}
			log.info("Started.");
		} catch (InterruptedException e) {
			log.error("Fail to start.", e);
		}
	}

	public void shutdown() {
		BotLaunchableStatus targetStatus = BotLaunchableStatus.OFF;
		try {
			this.stop();
			synchronized (this) {
				this.status = targetStatus;
				this.notify();
			}
			log.info("Stopped.");
		} catch (InterruptedException e) {
			log.error("Fail to stop", e);
		}
	}

	private void awaitStatus(BotLaunchableStatus status) throws InterruptedException {
		while (!status.equals(this.status)) {
			synchronized (this) {
				this.wait();
			}
		}
	}

	public void awaitRunning() throws InterruptedException {
		this.awaitStatus(BotLaunchableStatus.ON);
	}

	public void awaitShuttingDown() throws InterruptedException {
		this.awaitStatus(BotLaunchableStatus.OFF);
	}

	public Logger getLog() {
		return log;
	}

	public BotLaunchableStatus getStatus() {
		return status;
	}

	public abstract void start() throws InterruptedException;

	public abstract void stop() throws InterruptedException;

}
