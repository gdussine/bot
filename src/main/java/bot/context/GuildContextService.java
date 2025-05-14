package bot.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.persistence.EntityRepository;
import bot.service.core.BotService;
import bot.service.core.BotServiceInfo;
import net.dv8tion.jda.api.entities.Guild;

@BotServiceInfo
public class GuildContextService extends BotService implements GuildContextProvider {

	Map<Long, GuildContext> contexts = new HashMap<>();

	private EntityRepository<GuildContextEntry> repository;

	public GuildContextService() {
		this.repository = new EntityRepository<>(GuildContextEntry.class, this);
	}

	public Map<Long, GuildContext> getContexts() {
		if (contexts == null) {
			this.init();
		}
		return contexts;
	}

	public void init() {
		this.contexts = new HashMap<>();
		for (GuildContextEntry configurationEntry : repository.all()) {
			this.loadEntry(configurationEntry);
		}
		this.log.info("{} contexts have been initialized", contexts.size());
	}

	public void loadEntry(GuildContextEntry entry) {
		GuildContext context = getContext(entry.getGuildId());
		context.put(entry);
		getContexts().put(entry.getGuildId(), context);
	}
	
	public void updateEntry(GuildContextEntry newEntry) {
		GuildContextEntry oldEntry = repository.one((root, builder) ->{
			return List.of(
					builder.equal(root.get("contextKey"), newEntry.getContextKey()),
					builder.equal(root.get("guildId"), newEntry.getGuildId()));
		});
		if(oldEntry == null) {
			repository.persist(newEntry);
		} else {
			newEntry.setId(oldEntry.getId());
			repository.merge(newEntry);
		}
		getContext(newEntry.getGuildId()).put(newEntry);
	}


	public GuildContext getContext(long guildId) {
		GuildContext context = getContexts().get(guildId);
		if (context == null) {
			context = new GuildContext(getBot(), guildId);
			getContexts().put(guildId, context);
		}
		return context;
	}

	public GuildContext getContext(Guild guild) {
		return getContext(guild.getIdLong());
	}

}
