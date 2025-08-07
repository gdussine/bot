package bot.command.impl;

import java.util.List;

import bot.command.annotations.CommandDescription;
import bot.command.annotations.CommandModule;
import bot.command.core.CommandAction;
import bot.pagination.PaginationService;
import bot.view.impl.SimplePaginationView;

@CommandModule(name = "test", permission = {})
public class TestAction extends CommandAction {

    @CommandDescription("Test functionalities")
    public void test() {
        List<Object> numbers = List.of(1, 2, 3, 4, 5, 6, 7);
        this.bot.getService(PaginationService.class).create(new SimplePaginationView(), numbers, 3, interaction);
    }
}
