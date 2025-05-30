package bot.view.impl;

import bot.context.GuildContext;
import bot.view.BotView;

public class ContextActionView extends BotView {

    public ContextActionView() {
        this.template.setColor(blue);
    }

    protected ContextActionView setContextTitle(String title) {
        this.template.setTitle(":globe_with_meridians: %s".formatted(title));
        return this;
    }

    protected ContextActionView setCondtextDescription(GuildContext context){
        context.getContextMap().forEach((key, entry)->{
            this.template.appendDescription("- __%s__: %s\n".formatted(key, entry.getContextValue()));
        });
        return this;
    }

    public ContextActionView toContextAllView(GuildContext context){
        return this.setContextTitle("Context").setCondtextDescription(context);
    }



}
