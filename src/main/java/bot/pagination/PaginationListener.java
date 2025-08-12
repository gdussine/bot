package bot.pagination;

import bot.api.simple.TemplateBotListener;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class PaginationListener extends TemplateBotListener{


    
    public PaginationService getPaginationService() {
        return bot.getService(PaginationService.class);
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        getPaginationService().onButtonClick(event);
    }



    
}
