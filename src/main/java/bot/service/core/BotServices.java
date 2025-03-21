package bot.service.core;

import java.util.HashMap;
import java.util.Map;

public class BotServices {

    private Map<Class<? extends GenericBotService>, GenericBotService> services = new HashMap<>();

    public <T extends GenericBotService> void put(T service){
        services.put(service.getClass(), service);
    }

    public <T extends GenericBotService> T get(Class<T> serviceClass) {
        return serviceClass.cast(services.get(serviceClass));
    }

}
