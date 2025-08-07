package bot.utils;

import net.dv8tion.jda.api.entities.emoji.Emoji;

public enum DiscordEmoji {

    ARROW_LEFT("\u2B05"),
    ARROW_RIGHT("\u27A1"),
    PAGE_WITH_CURL("\uD83D\uDCC3");
    ;


    private final String code;

    DiscordEmoji(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Emoji getEmoji(){
        return Emoji.fromUnicode(code);
    }

    @Override
    public String toString() {
        return code;
    }
}
