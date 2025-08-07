package bot.core;

import net.dv8tion.jda.api.entities.emoji.Emoji;

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

    public Emoji getEmoji(){
        switch (this) {
            case CREATED:
                return Emoji.fromFormatted("U+1F535");
            case RUNNING:
                return Emoji.fromFormatted("U+1F7E2");
            default:
                return Emoji.fromFormatted("U+26AB");
        }
    }

}
