package bot.context;

import java.util.List;

public interface GuildContextIndexList {

    public List<String> getTextChannelIndexList();

    public List<String> getVoiceChannelIndexList();

    public List<String> getRoleIndexList();

    public List<String> getEmojiIndexList();
    

}
