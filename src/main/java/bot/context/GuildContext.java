package bot.context;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;

public class GuildContext {

    Guild guild;
    Map<String, TextChannel> textChannels = new HashMap<>();
    Map<String, VoiceChannel> voiceChannels = new HashMap<>();
    Map<String, Role> roles = new HashMap<>();
    Map<String, RichCustomEmoji> emojis = new HashMap<>();

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public Map<String, TextChannel> getTextChannels() {
        return textChannels;
    }

    public TextChannel getTextChannel(Enum<?> key){
        return textChannels.get(key.name());
    }

    public void setTextChannels(Map<String, TextChannel> textChannels) {
        this.textChannels = textChannels;
    }

    public Map<String, VoiceChannel> getVoiceChannels() {
        return voiceChannels;
    }

    public VoiceChannel getVoiceChannel(Enum<?> key){
        return voiceChannels.get(key.name());
    }

    public void setVoiceChannels(Map<String, VoiceChannel> voiceChannels) {
        this.voiceChannels = voiceChannels;
    }

    public Map<String, Role> getRoles() {
        return roles;
    }

    public Role getRole(Enum<?> key){
        return roles.get(key.name());
    }

    public void setRoles(Map<String, Role> roles) {
        this.roles = roles;
    }

    public Map<String, RichCustomEmoji> getEmojis() {
        return emojis;
    }

    public RichCustomEmoji getEmoji(Enum<?> key){
        return  emojis.get(key.name());
    }

    public void setEmojis(Map<String, RichCustomEmoji> emojis) {
        this.emojis = emojis;
    }

}
