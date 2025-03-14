package bot.core;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class BotServices {

    private Map<Class<? extends BotService>, BotService> services = new HashMap<>();
    private Logger log = LoggerFactory.getLogger(getClass());

    public void register(Bot bot) {
        try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(BotService.class)) {
                if (classInfo.isAbstract())
                    continue;
                Class<? extends BotService> clazz = classInfo.loadClass(BotService.class);
                try {
                    BotService service = clazz.getConstructor().newInstance();
                    service.connect(bot);
                    services.put(service.getClass(), service);
                    log.info("{} registered",clazz.getSimpleName());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public void connect(Bot bot){
        for(BotService service : services.values()){
            service.connect(bot);
        }
    }

    public <T extends BotService> void set(T service){
        services.put(service.getClass(), service);
    }

    public <T extends BotService> T get(Class<T> serviceClass) {
        return serviceClass.cast(services.get(serviceClass));
    }

}
