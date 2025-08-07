package bot.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import bot.api.GuildContext;
import bot.persistence.EntityService;
import bot.service.impl.SimpleBotService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.dv8tion.jda.api.entities.Guild;

public class GuildContextService extends SimpleBotService {

	private Map<Long, GuildContext> contexts;

	private List<GuildContextKeyProvider> providers;
	private List<GuildContextKey> keys;

	private GuildContextValueDAO valueDAO;
	private GuildContextKeyDAO keyDAO;

	public GuildContextService() {
		this.keys = new ArrayList<>();
		this.providers = new ArrayList<>();
		this.valueDAO = new GuildContextValueDAO();
		this.keyDAO = new GuildContextKeyDAO();
	}

	public void registerDAO() {
		bot.getRunningService(EntityService.class).registerDAO(valueDAO, keyDAO);
	}

	@Override
	public void start() {
		this.initKeyProviders();
		this.registerDAO();
		this.initKeys();
		this.initContexts();
	}

	public void initKeyProviders() {
		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
			for (ClassInfo classInfo : result.getClassesImplementing(GuildContextKeyProvider.class)) {
				if (classInfo.isAbstract())
					continue;
				Class<GuildContextKeyProvider> providerClass = classInfo.loadClass(GuildContextKeyProvider.class);
				try {
					this.providers.add(providerClass.getConstructor().newInstance());
				} catch (Exception e) {
					this.logger.error(e.getMessage());
				}
			}
		}
	}

	public void initKeys() {
		List<GuildContextKey> providersKeys = new ArrayList<>();
		providers.forEach(provider -> providersKeys.addAll(provider.provide()));
		this.keys = keyDAO.updateKeys(providersKeys);
		getLogger().info("Keys {}", keys);
	}

	public void initContexts() {
		this.contexts = new HashMap<>();
		for (GuildContextValue entry : valueDAO.all()) {
			GuildContext context = contexts.getOrDefault(entry.getGuildId(),
					new GuildContextImpl(bot, entry.getGuildId()));
			context.put(entry);
			contexts.put(entry.getGuildId(), context);
		}
		getLogger().info("Initialized {} guilds.", contexts.size());
	}

	public void setContextEntry(Guild guild, String keyString, String valueString) throws GuildContextException {
		GuildContextKey key = keyDAO.find(keyString).orElse(null);
		if(key == null)
			throw GuildContextException.unknownKey(keyString);
		GuildContextValue detachedValue = new GuildContextValue(guild.getIdLong(), key, valueString);
		GuildContextValue oldValue = getContext(guild.getIdLong()).getValue(keyString);
		if (oldValue != null) {
			detachedValue.setId(oldValue.getId());
		}
		GuildContextValue attachedValue = valueDAO.merge(detachedValue);
		this.getContext(guild.getIdLong()).put(attachedValue);
	}

	public void removeContextEntry(Guild guild, String key) {
		GuildContext context = getContext(guild);
		Optional.ofNullable(context.getValue(key)).ifPresent(cacheValue -> {
			context.remove(key);
			valueDAO.delete(cacheValue.getId());
		});
	}

	public GuildContext getContext(long guildId) {
		handler.awaitRunning().join();
		GuildContext context = contexts.get(guildId);
		if (context == null) {
			context = new GuildContextImpl(getBot(), guildId);
			contexts.put(guildId, context);
		}
		return context;
	}

	public GuildContext getContext(Guild guild) {
		return getContext(guild.getIdLong());
	}

	public List<GuildContextKey> getKeys() {
		return keys;
	}

}
