package bot.view.impl;

import java.util.Collection;

import bot.service.BotService;
import bot.view.BotView;

public class SystemView extends BotView{

    private final String SHUTDOWN_LINE = "The bot will shutdown now.";
    private final String SERVICE_LINE = "- %s: %s\n";
    private final String NETWORK_LINE = "- %s: %dms";
    private final String TITLE = ":desktop:  %s";
    private final String SERVICES_TITLE = "Services";
    private final String NETWORK_TITLE = "Network";
    private final String SHUTDOWN_TITLE = "Shutdown";

    public SystemView(){
        this.template.setColor(blue);
    }

    protected SystemView setSystemTitle(String title){
        this.template.setTitle(TITLE.formatted(title));
        return this;
    }

    public SystemView toNetworkView(long discordPing){
        this.template.setDescription(NETWORK_LINE.formatted("Discord", discordPing));
        return this.setSystemTitle(NETWORK_TITLE);
    }

    public SystemView toShutdownView(){
        this.template.setDescription(SHUTDOWN_LINE);
        return this.setSystemTitle(SHUTDOWN_TITLE);
    }

    public SystemView toServicesView(Collection<BotService> services) {
        StringBuilder sb = new StringBuilder();
        services.forEach(service -> sb.append(
                SERVICE_LINE.formatted(service.getClass().getSimpleName(), service.getStatus().isOn() ? "ON" : "OFF")));
        template.setDescription(sb.toString());
        return this.setSystemTitle(SERVICES_TITLE);
    }

}
