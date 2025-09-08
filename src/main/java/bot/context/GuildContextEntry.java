package bot.context;

import io.github.gdussine.bot.api.GuildContextKeyed;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GuildContextEntry implements GuildContextKeyed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long guildId;

    private String contextKey;

    private String contextValue;

    public GuildContextEntry(Long guildId, String contextKey, String contextValue) {
        this.guildId = guildId;
        this.contextKey = contextKey;
        this.contextValue = contextValue;
    }

    public GuildContextEntry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public void setContextKey(String contextKey) {
        this.contextKey = contextKey;
    }

    public String getContextValue() {
        return contextValue;
    }

    public void setContextValue(String contextValue) {
        this.contextValue = contextValue;
    }

    @Override
    public String getContextKey() {
        return contextKey;
    }

}
