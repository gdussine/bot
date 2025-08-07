package bot.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import bot.service.impl.SimpleBotService;
import bot.utils.DiscordEmoji;
import bot.view.PaginationView;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.messages.MessageRequest;

public class PaginationService extends SimpleBotService {

    private Map<Long, PaginationContainer<?>> cache;
    private final String buttonCategory = "pagination";

    private final String buttonFormat = "%s_%s";

    private Button nextButton = Button.primary(buttonFormat.formatted(buttonCategory, "next"),
            DiscordEmoji.ARROW_RIGHT.getEmoji());

    private Button previousButton = Button.primary(buttonFormat.formatted(buttonCategory, "previous"),
            DiscordEmoji.ARROW_LEFT.getEmoji());

    public PaginationService() {
        cache = new HashMap<>();
    }

    public <X> void create(PaginationView<X> template, List<X> data, int pageSize,
            SlashCommandInteraction interaction) {
        PaginationContainer<X> container = new PaginationContainer<>(template, data, pageSize);
        ReplyCallbackAction action = interaction.replyEmbeds(template.render());
        addButtons(container, action);
        action.submit().thenCompose(hook -> hook.retrieveOriginal().submit())
                .thenAccept(msg -> cache.put(msg.getIdLong(), container));
    }

    public void onButtonClick(ButtonInteractionEvent event) {
        if (!event.getButton().getId().startsWith(buttonCategory)) {
            return;
        }
        if (event.getButton().getId().equals(nextButton.getId())) {
            this.update(event.getInteraction(), container -> container.nextPage());
        } else {
            this.update(event.getInteraction(), container -> container.previousPage());
        }
    }

    public void update(ButtonInteraction interaction, Consumer<PaginationContainer<?>> consumer) {
        PaginationContainer<?> container = cache.get(interaction.getMessageIdLong());
        if (container == null) {
            interaction.getMessage().getEmbeds().stream().findFirst()
                    .ifPresentOrElse(
                            embed -> interaction.editMessageEmbeds(embed).setComponents().submit(),
                            () -> interaction.reply("No embed found.").submit());
            return;
        }
        consumer.accept(container);
        MessageEmbed embed = container.getTemplate().render();
        MessageEditCallbackAction action = interaction.editMessageEmbeds(embed);
        this.addButtons(container, action);
        action.submit();
    }

    public void addButtons(PaginationContainer<?> container, MessageRequest<?> action) {
        if (container == null) {
            action.setComponents();
            return;
        }
        List<Button> buttons = new ArrayList<>();
        buttons.add(container.hasPreviousPage() ? previousButton : previousButton.asDisabled());
        buttons.add(container.hasNextPage() ? nextButton : nextButton.asDisabled());
        action.setComponents(ActionRow.of(buttons));
    }



}
