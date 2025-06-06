package bot.context;

import java.util.HashMap;
import java.util.Map;

import bot.persistence.EntityRepository;
import bot.service.BotService;
import bot.service.BotServiceInfo;
import net.dv8tion.jda.api.entities.Guild;

@BotServiceInfo
public class GuildContextService extends BotService {

	Map<Long, GuildContext> contexts;

	private EntityRepository<GuildContextEntry> repository;

	public Map<Long, GuildContext> getContexts() {
		return contexts;
	}

	@Override
	public void start() {
		this.repository = new EntityRepository<>(GuildContextEntry.class, this);
		this.initContexts();
	}

	public void initContexts() {
		this.contexts = new HashMap<>();
		for (GuildContextEntry entry : repository.all()) {
			GuildContext context = contexts.getOrDefault(entry.getGuildId(), new GuildContext(bot, entry.getGuildId()));
			context.putEntry(entry);
			contexts.put(entry.getGuildId(), context);
		}
		this.log.info("Initialized {} guilds.", contexts.size());
	}

	public void setContextEntry(Guild guild, String key, String value) {
		GuildContextEntry newEntry = new GuildContextEntry(guild.getIdLong(), key, value);
		GuildContextEntry oldEntry = this.getContext(newEntry.getGuildId()).getEntry(newEntry.getContextKey());
		if (oldEntry == null) {
			repository.persist(newEntry);
		} else {
			newEntry.setId(oldEntry.getId());
			repository.merge(newEntry);
		}
		this.getContext(newEntry.getGuildId()).putEntry(newEntry);
		this.log.info("Set {} to \"{}\" for {}.",
				newEntry.getContextKey(),
				newEntry.getContextValue(),
				bot.getJda().getGuildById(newEntry.getGuildId()).getName());
	}

	public void removeContextEntry(Guild guild, String key) {
		GuildContext context = getContext(guild);
		GuildContextEntry oldEntry = context.getEntry(key);
		context.remove(key);
		repository.delete(oldEntry);
	}

	public GuildContext getContext(long guildId) {
		this.awaitRunning().join();
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
