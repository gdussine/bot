package bot.context;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GuildContextValue{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long guildId;
    private String contextValue;

    @ManyToOne
    @JoinColumn(name = "contextKey", nullable = false)
    private GuildContextKey contextKey;

    public GuildContextValue(Long guildId, GuildContextKey contextKey,  String contextValue) {
        this.guildId = guildId;
        this.contextKey = contextKey;
        this.contextValue = contextValue;
    }

    public GuildContextValue() {
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

    public String getContextValue() {
        return contextValue;
    }

    public void setContextValue(String contextValue) {
        this.contextValue = contextValue;
    }

    public GuildContextKey getContextKey() {
        return contextKey;
    }

    public void setContextKey(GuildContextKey contextKey) {
        this.contextKey = contextKey;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((guildId == null) ? 0 : guildId.hashCode());
        result = prime * result + ((contextValue == null) ? 0 : contextValue.hashCode());
        result = prime * result + ((contextKey == null) ? 0 : contextKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GuildContextValue other = (GuildContextValue) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (guildId == null) {
            if (other.guildId != null)
                return false;
        } else if (!guildId.equals(other.guildId))
            return false;
        if (contextValue == null) {
            if (other.contextValue != null)
                return false;
        } else if (!contextValue.equals(other.contextValue))
            return false;
        if (contextKey == null) {
            if (other.contextKey != null)
                return false;
        } else if (!contextKey.equals(other.contextKey))
            return false;
        return true;
    }

}
