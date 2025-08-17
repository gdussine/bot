package bot.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import bot.api.BotService;
import bot.core.BotLaunchableStatus;

public class BotServiceHandler {

    protected BotService service;
    protected AtomicReference<BotLaunchableStatus> synchronizedStatus;
    protected ConcurrentHashMap<BotLaunchableStatus, CompletableFuture<Void>> waiters;

    public BotServiceHandler(BotService service) {
        this.service = service;
        this.synchronizedStatus = new AtomicReference<>(BotLaunchableStatus.CREATED);
        this.waiters = new ConcurrentHashMap<>();
        this.waiters.put(BotLaunchableStatus.RUNNING, createWaiter(BotLaunchableStatus.RUNNING));
        this.waiters.put(BotLaunchableStatus.SHUTDOWN, createWaiter(BotLaunchableStatus.SHUTDOWN));
    }
        
    private CompletableFuture<Void> createWaiter(BotLaunchableStatus status){
    	return new CompletableFuture<Void>().whenComplete((v, err) -> {
            if (err != null)
                this.service.getLogger().error("Not %s".formatted(status.name()), err);
    	});
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
            this.service.start();
            this.setStatus(BotLaunchableStatus.RUNNING);
            this.service.getLogger().info("Started.");
        } catch (Exception e) {
            this.service.getLogger().error("Failed to run", e);
            ;
        }
    }

    public void shutdown() {
        try {
            this.service.stop();
            this.setStatus(BotLaunchableStatus.SHUTDOWN);
            this.service.getLogger().info("Stopped.");
        } catch (Exception e) {
            this.service.getLogger().error("Failed to shutdown", e);
        }
    }

    private CompletableFuture<Void> awaitStatus(BotLaunchableStatus status) {
        if (synchronizedStatus.get() == status)
            return CompletableFuture.completedFuture(null);
        return waiters.get(status);
    }  

    public CompletableFuture<Void> awaitRunning() {
        return this.awaitStatus(BotLaunchableStatus.RUNNING);
    }

    public CompletableFuture<Void> awaitShuttingDown() {
        return this.awaitStatus(BotLaunchableStatus.SHUTDOWN);
    }

}
