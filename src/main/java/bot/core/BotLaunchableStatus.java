package bot.core;

public enum BotLaunchableStatus {

    ON,
    OFF;

    public boolean isOn(){
        return ON.equals(this);
    }

    public boolean isOff(){
        return OFF.equals(this);
    }

}
