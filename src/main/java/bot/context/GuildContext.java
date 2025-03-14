package bot.context;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;

public class GuildContext {

    private Guild guild;
    private Map<String, Long> textChannelIds = new HashMap<>();
    private Map<String, Long> voiceChannelIds = new HashMap<>();
    private Map<String, Long> roleIds = new HashMap<>();
    private Map<String, Long> emojiIds = new HashMap<>();

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public Map<String, Long> getTextChannelIds() {
        return textChannelIds;
    }

    public TextChannel getTextChannel(String key){
        return guild.getTextChannelById(textChannelIds.getOrDefault(key, 0L));
    }

    public void setTextChannelIds(Map<String, Long> textChannelIds) {
        this.textChannelIds = textChannelIds;
    }

    public Map<String, Long> getVoiceChannelIds() {
        return voiceChannelIds;
    }

    public VoiceChannel getVoiceChannel(String key){
        return guild.getVoiceChannelById(voiceChannelIds.getOrDefault(key, 0L));
    }

    public void setVoiceChannelIds(Map<String, Long> voiceChannelIds) {
        this.voiceChannelIds = voiceChannelIds;
    }

    public Map<String, Long> getRoleIds() {
        return roleIds;
    }

    public Role getRole(String key){
        return guild.getRoleById(roleIds.getOrDefault(key, 0L));
    }

    public void setRoleIds(Map<String, Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Map<String, Long> getEmojiIds() {
        return emojiIds;
    }

    public RichCustomEmoji getEmoji(String key){
        return guild.getEmojiById(emojiIds.getOrDefault(key, 0L));
    }

    public void setEmojiIds(Map<String, Long> emojiIds) {
        this.emojiIds = emojiIds;
    }

}
