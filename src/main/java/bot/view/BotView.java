package bot.view;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class BotView {

    protected EmbedBuilder template = new EmbedBuilder();

    protected Color red = new Color(0xFF0033);
    protected Color green = new Color(0x00C851);
    protected Color blue = new Color(0x0076D1);
    protected Color yellow = new Color(0xFFD93D);
    protected Color black = new Color(0x000001);
    protected Color white = new Color(0xFFFFFF);

    public MessageEmbed render() {
        return template.build();
    }

}
