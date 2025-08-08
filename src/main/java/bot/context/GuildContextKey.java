package bot.context;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class GuildContextKey implements GuildContextKeyed{

    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuildContextType contextType;

    public GuildContextKey() {
    }

    public GuildContextKey(GuildContextType contextType, String name) {
        this.contextType = contextType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GuildContextType getContextType() {
        return contextType;
    }

    public void setContextType(GuildContextType contextType) {
        this.contextType = contextType;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public GuildContextKey getKey() {
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        GuildContextKey other = (GuildContextKey) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
