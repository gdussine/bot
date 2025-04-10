package bot.context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import bot.persistence.EntityRepository;
import bot.service.core.AbstractBotService;
import bot.service.core.BotService;
import net.dv8tion.jda.api.entities.Guild;

@BotService
public class GuildContextService extends AbstractBotService implements GuildContextProvider {

    Map<Long, GuildContext> contexts;

    private EntityRepository<GuildConfiguration> repository;

    public GuildContextService() {
        this.repository = new EntityRepository<>(GuildConfiguration.class, this);
    }

    public Map<Long, GuildContext> getContexts() {
        if (contexts == null) {
            this.init();
        }
        return contexts;
    }

    public void init() {
        contexts = new HashMap<>();
        for (GuildConfiguration configuration : repository.all()) {
            this.load(configuration);
        }
        this.log.info("{} configurations have been initialized", contexts.size());
    }

    public GuildContext load(GuildConfiguration configuration){
        GuildContext context = new GuildContext(getBot(), configuration);
        contexts.put(configuration.getId(), context);
        this.log.info("Configuration from Guild:{} has been loaded.", configuration.getId());
        return context;
    }

    public GuildContext create(long guildId){
        GuildConfiguration configuration = new GuildConfiguration(guildId);
        repository.persist(configuration);
        this.log.info("Configuration from Guild:{} has been created. ({})", guildId, configuration.getCreated());
        return this.load(configuration);
    }

    public GuildContext update(GuildContext context){
        context.getConfiguration().setUpdated(LocalDateTime.now());
        repository.merge(context.getConfiguration());
        this.log.info("Configuration from Guild:{} has been updated. ({}).", context.getGuild().getIdLong(), context.getConfiguration().getUpdated());
        return context;
    }

    public GuildContext getContext(long guildId) {
        GuildContext context = getContexts().get(guildId);
        if (context == null){
            context = this.create(guildId);
        }
        
        return context;
    }

    public GuildContext getContext(Guild guild) {
        return getContext(guild.getIdLong());
    }

}
