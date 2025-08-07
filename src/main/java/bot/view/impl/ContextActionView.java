package bot.view.impl;

import java.util.List;

import bot.api.GuildContext;
import bot.context.GuildContextKey;
import bot.view.BotView;

public class ContextActionView extends BotView {

    public ContextActionView() {
        this.template.setColor(blue);
    }

    protected ContextActionView setContextTitle(String title) {
        this.template.setTitle(":globe_with_meridians: %s".formatted(title));
        return this;
    }

    protected ContextActionView setCondtextDescription(List<GuildContextKey> keys, GuildContext context) {
        keys.forEach(key -> {
            this.template.appendDescription("```yaml\n%s: %s\n```\n".formatted(key,
                    context.getString(key.getName()) == null ? "null" : "\"%s\"".formatted(context.getString(key.getName()))));
        });
        return this;
    }

    public ContextActionView toContextAllView(List<GuildContextKey> keys, GuildContext context) {
        return this.setContextTitle("CONTEXT").setCondtextDescription(keys, context);
    }

}
