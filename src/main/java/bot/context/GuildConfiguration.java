package bot.context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;

@Entity
public class GuildConfiguration {

    @Id
    private Long id;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ElementCollection
    @CollectionTable(name = "GuildConfigurationTextChannel", joinColumns = {
            @JoinColumn(name = "configurationId", referencedColumnName = "id") })
    @MapKeyColumn(name = "configurationLabel")
    @Column(name = "configurationKey")
    private Map<String, Long> textChannels = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "GuildConfigurationVoiceChannel", joinColumns = {
            @JoinColumn(name = "configurationId", referencedColumnName = "id") })
    @MapKeyColumn(name = "configurationLabel")
    @Column(name = "configurationKey")
    private Map<String, Long> voiceChannels = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "GuildConfigurationRole", joinColumns = {
            @JoinColumn(name = "configurationId", referencedColumnName = "id") })
    @MapKeyColumn(name = "configurationLabel")
    @Column(name = "configurationKey")
    private Map<String, Long> roles = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "GuildConfigurationEmoji", joinColumns = {
            @JoinColumn(name = "configurationId", referencedColumnName = "id") })
    @MapKeyColumn(name = "configurationLabel")
    @Column(name = "configurationKey")
    private Map<String, Long> emojis = new HashMap<>();

    public GuildConfiguration() {

    }

    public GuildConfiguration(Long id) {
        this.id = id;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Long> getTextChannels() {
        return textChannels;
    }

    public void setTextChannels(Map<String, Long> textChannels) {
        this.textChannels = textChannels;
    }

    public Map<String, Long> getVoiceChannels() {
        return voiceChannels;
    }

    public void setVoiceChannels(Map<String, Long> voiceChannels) {
        this.voiceChannels = voiceChannels;
    }

    public Map<String, Long> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Long> roles) {
        this.roles = roles;
    }

    public Map<String, Long> getEmojis() {
        return emojis;
    }

    public void setEmojis(Map<String, Long> emojis) {
        this.emojis = emojis;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

}
