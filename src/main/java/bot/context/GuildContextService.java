package bot.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.apiold.framework.TemplateBotService;
import bot.persistence.EntityService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.gdussine.bot.api.GuildContext;
import io.github.gdussine.bot.api.GuildContextKeyed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;

public class GuildContextService extends TemplateBotService {

	private List<GuildContextKeyed> definedKeys;

	private Map<Long, GuildContext> contexts;

	private GuilContextEntryDAO dao;

	public GuildContextService() {
		this.contexts = new HashMap<>();
		this.definedKeys = new ArrayList<>();
	}

	@Override
	public void onStart() {
		this.initDAO();
		this.initDefinedKeys();
		this.initContexts();
	}

	private void initDAO() {
		dao = bot.getStartedService(EntityService.class).registerDAO(new GuilContextEntryDAO());
	}

	private void initDefinedKeys() {
		try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
			for (ClassInfo classInfo : result.getClassesImplementing(GuildContextKeyed.class).getEnums()) {
				Class<? extends GuildContextKeyed> clazz = classInfo.loadClass(GuildContextKeyed.class);
				if (!clazz.isEnum())
					continue;
				Arrays.stream(clazz.getEnumConstants()).forEach(keyed -> definedKeys.add(keyed));
			}
		}
		getLogger().info("ContextKeys {}", definedKeys);
	}

	private void initContexts() {
		dao.all().forEach(entry -> {
			GuildContext context = contexts.get(entry.getGuildId());
			if (context == null) {
				context = new GuildContextImpl(bot, entry.getGuildId());
				contexts.put(entry.getGuildId(), context);
			}
			context.put(entry, entry.getContextValue());
		});
	}

	public void initGuild(GuildReadyEvent event){
		contexts.putIfAbsent(event.getGuild().getIdLong(), new GuildContextImpl(bot, event.getGuild().getIdLong()));
	}

	public GuildContext getContext(Long guildId) {
		return contexts.get(guildId);
	}

	public GuildContext getContext(Guild guild) {
		return this.getContext(guild.getIdLong());
	}

	public List<GuildContextKeyed> getDefinedKeys() {
		return definedKeys;
	}

	public void editContextEntry(Long guildId, String key, String value) {
		GuildContextEntry editedEntry = dao.withTransaction(em -> {
			GuildContextEntry entry = dao.find(em, guildId, key).orElse(new GuildContextEntry(guildId, key, value));
			entry.setContextValue(value);
			return em.merge(entry);
		});
		getContext(guildId).put(editedEntry, editedEntry.getContextValue());
		getLogger().info("Key {} set {} in Guild {}.", key, value, guildId);
	}

	public void removeContextEntry(Long guildId, String key) {
		GuildContextEntry removedEntry = dao.withTransaction(em -> {
			GuildContextEntry entry = dao.find(em, guildId, key).orElse(null);
			em.remove(entry);
			return entry;
		});
		getContext(guildId).remove(removedEntry);
		getLogger().info("Key {} removed from Guild {}.", key, guildId);
	}
}
