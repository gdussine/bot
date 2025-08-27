package bot.view.impl;

import java.util.List;

import bot.api.GuildContext;
import bot.context.GuildContextKey;
import bot.view.BotView;

public class ContextView extends BotView {

    public ContextView(List<GuildContextKey> keys, GuildContext context) {
        this.template.setColor(blue);
        this.template.setTitle(":globe_with_meridians: %s".formatted("CONTEXT"));
        this.setCondtextDescription(keys, context);
    }

    protected ContextView setCondtextDescription(List<GuildContextKey> keys, GuildContext context) {
        keys.forEach(key -> {
            try {
                String value = context.isDefine(key) ? context.getAsMention(key) : "null";
                this.template.addField(key.getName(), value, true);
            } catch (Exception e) {
                this.template.addField(key.getName(), e.getMessage(), true);
            }
        });
        return this;
    }
}
