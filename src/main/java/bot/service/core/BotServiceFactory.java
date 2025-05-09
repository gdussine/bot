package bot.service.core;

import java.util.HashMap;
import java.util.Map;

import bot.core.Bot;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class BotServiceFactory {

    private Map<Class<? extends AbstractBotService>, AbstractBotService> services = new HashMap<>();

    private Bot bot;

    public BotServiceFactory(Bot bot){
        this.bot = bot;
    }

    public void createAll(){
        try (ScanResult result = new ClassGraph().enableAnnotationInfo().scan()) {
            for (ClassInfo classInfo : result.getSubclasses(AbstractBotService.class)) {
                if (classInfo.isAbstract())
                    continue;
                if (!classInfo.hasAnnotation(BotService.class))
                    continue;
                Class<? extends AbstractBotService> clazz = classInfo.loadClass(AbstractBotService.class);
                this.create(clazz);
            }
        }
    }

    public void connectAll(){
        for(AbstractBotService service : services.values()){
            service.connect(bot);
        }
    }

    public <T extends AbstractBotService> void create(Class<T> clazz) {
        try {
            BotService info = clazz.getAnnotation(BotService.class);
            AbstractBotService service = clazz.getConstructor().newInstance();
            services.put(service.getClass(), service);
            service.setListener(info.listener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends AbstractBotService> T get(Class<T> serviceClass) {
        return serviceClass.cast(services.get(serviceClass));
    }

    public Map<Class<? extends AbstractBotService>, AbstractBotService> getServices() {
        return services;
    }

    public void close(){
        for(AbstractBotService service : services.values()){
            service.disconnect();
        }
    }

}
