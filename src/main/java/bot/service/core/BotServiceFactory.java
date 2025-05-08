package bot.service.core;

import java.util.HashMap;
import java.util.Map;

import bot.core.Bot;

public class BotServiceFactory {

    private Map<Class<? extends AbstractBotService>, AbstractBotService> services = new HashMap<>();

    public <T extends AbstractBotService> void create(Class<T> clazz, Bot bot) {
        try {
            BotService info = clazz.getAnnotation(BotService.class);
            AbstractBotService service = clazz.getConstructor().newInstance();
            services.put(service.getClass(), service);
            service.setListener(info.listener());
            service.connect(bot);
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
