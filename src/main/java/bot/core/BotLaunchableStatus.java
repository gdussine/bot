package bot.core;

public enum BotLaunchableStatus {

    CREATED, 
    RUNNING,
    SHUTDOWN;

    public boolean isCreated(){
        return CREATED.equals(this);
    }

    public boolean isRunning(){
        return RUNNING.equals(this);
    }

    public boolean isShutDown(){
        return SHUTDOWN.equals(this);
    }

}
