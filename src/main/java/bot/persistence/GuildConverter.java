package bot.persistence;

import bot.core.Bot;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.dv8tion.jda.api.entities.Guild;

@Converter(autoApply = true)
public class GuildConverter implements AttributeConverter<Guild, Long> {

    @Override
    public Long convertToDatabaseColumn(Guild attribute) {
        if(attribute == null)
            return null;
        return attribute.getIdLong();
    }

    @Override
    public Guild convertToEntityAttribute(Long dbData) {
        return Bot.getInstance().getJDA().getGuildById(dbData);
    }


}
