package bot.view;

public class ExceptionView extends BotView {

    public ExceptionView(Exception exception) {
        this.template.setTitle(":no_entry: " + exception.getClass().getSimpleName());
        this.template.setDescription(exception.getMessage());
        this.template.setColor(red);
    }

}
