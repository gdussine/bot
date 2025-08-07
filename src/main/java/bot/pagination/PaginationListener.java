package bot.pagination;

import bot.service.impl.SimpleBotListener;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class PaginationListener extends SimpleBotListener{


    
    public PaginationService getPaginationService() {
        return bot.getService(PaginationService.class);
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        getPaginationService().onButtonClick(event);
    }



    
}
