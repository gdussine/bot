package bot.view.impl;

import bot.utils.DiscordEmoji;
import bot.view.PaginationView;

public class SimplePaginationView extends PaginationView<Object> {

    public SimplePaginationView(){
        this.template.setTitle(DiscordEmoji.PAGE_WITH_CURL.getCode() + " List");
        this.template.setColor(blue);
    }

    @Override
    protected PaginationView<Object> renderData() {
        StringBuilder sb = new StringBuilder();
        for(Object o : container.getData() ){
            sb.append("- ").append(o).append("\n");
        }
        template.setDescription(sb.toString());
        return this;
    }

}
