package bot.view.impl;

import java.util.List;

import bot.view.BotView;
import io.github.gdussine.bot.api.GuildContext;
import io.github.gdussine.bot.api.GuildContextKeyed;

public class ContextView extends BotView {

    public ContextView(List<GuildContextKeyed> keys, GuildContext context) {
        this.template.setColor(blue);
        this.template.setTitle(":globe_with_meridians: %s".formatted("CONTEXT"));
        this.setCondtextDescription(keys, context);
    }

    protected ContextView setCondtextDescription(List<GuildContextKeyed> keys, GuildContext context) {
        keys.forEach(key -> {
            try {
                String value = context.isDefine(key) ? context.getString(key) : "null";
                this.template.addField(key.getContextKey(), value, true);
            } catch (Exception e) {
                this.template.addField(key.getContextKey(), e.getMessage(), true);
            }
        });
        return this;
    }
}
