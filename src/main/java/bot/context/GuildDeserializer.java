package bot.context;

import java.io.IOException;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import bot.core.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class GuildDeserializer extends StdDeserializer<Guild> {

        private Supplier<Bot> supplier;

        public GuildDeserializer(Supplier<Bot> supplier) {
                this(null, supplier);
        }

        public GuildDeserializer(Class<?> vc, Supplier<Bot> supplier) {
                super(vc);
                this.supplier = supplier;
        }

        @Override
        public Guild deserialize(JsonParser jp, DeserializationContext ctxt)
                        throws IOException, JacksonException {
                JsonNode node = jp.getCodec().readTree(jp);
                return supplier.get().getJDA().getGuildById(node.asLong());
        }

}
