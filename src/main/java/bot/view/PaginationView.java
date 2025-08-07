package bot.view;

import bot.pagination.PaginationContainer;
import net.dv8tion.jda.api.entities.MessageEmbed;

public abstract class PaginationView<T> extends BotView{

    protected PaginationContainer<T> container;

    public PaginationView<T> setContainer(PaginationContainer<T> container){
        this.container = container;
        return this;
    }

    protected abstract PaginationView<T> renderData();

    @Override
    public MessageEmbed render() {
        this.renderData();
        return super.render();
    }




}
