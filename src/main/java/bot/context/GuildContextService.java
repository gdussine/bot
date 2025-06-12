package bot.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bot.service.BotService;
import bot.service.BotServiceInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.dv8tion.jda.api.entities.Guild;


@BotServiceInfo
public class GuildContextService extends BotService {


	private Set<String> keys;
	private	Map<Long, GuildContext> contexts;

	public GuildContextService(){
		this.keys = new HashSet<>();
	}

	public Map<Long, GuildContext> getContexts() {
		return contexts;
	}

	@Override
	public void start() {
		this.initKeys();
		this.initContexts();
	}

	public void initKeys(){
		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getClassesImplementing(GuildContextKeyProvider.class)) {
                if (classInfo.isAbstract())
                    continue;
                Class<GuildContextKeyProvider> providerType = classInfo.loadClass(GuildContextKeyProvider.class);
                try {
					GuildContextKeyProvider provider = providerType.getConstructor().newInstance();
					Set<String> providerKeys = provider.provide(); 
					keys.addAll(providerKeys);
					this.log.info("Collected {}.", providerKeys);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
		}

	}

	public void initContexts() {
		this.contexts = new HashMap<>();
		for (GuildContextEntry entry : getRepository(GuildContextEntry.class).all()) {
			GuildContext context = contexts.getOrDefault(entry.getGuildId(), new GuildContext(bot, entry.getGuildId()));
			context.putEntry(entry);
			contexts.put(entry.getGuildId(), context);
		}
		this.log.info("Initialized {} guilds.", contexts.size());
	}

	public void setContextEntry(Guild guild, String key, String value) {

		GuildContextEntry newEntry = new GuildContextEntry(guild.getIdLong(), key, value);
		GuildContextEntry oldEntry = this.getContext(newEntry.getGuildId()).getEntry(newEntry.getContextKey());
		if (oldEntry != null) {
			newEntry.setId(oldEntry.getId());
		}
		newEntry = getRepository(GuildContextEntry.class).merge(newEntry);
		this.getContext(newEntry.getGuildId()).putEntry(newEntry);
		this.log.info("Set {} to \"{}\" for {}.",
				newEntry.getContextKey(),
				newEntry.getContextValue(),
				bot.getJda().getGuildById(newEntry.getGuildId()).getName());
	}

	public void removeContextEntry(Guild guild, String key) {
		GuildContext context = getContext(guild);
		GuildContextEntry oldEntry = getRepository(GuildContextEntry.class).one(context.getEntry(key).getId());
		context.remove(key);
		getRepository(GuildContextEntry.class).delete(oldEntry);
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

	public Set<String> getKeys() {
		return keys;
	}

}
