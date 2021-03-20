package com.github.gdussine.react.model;

import java.util.Arrays;

public enum  Emotes {

    X("U+274c"),
    ARROW_LEFT("U+2b05U+fe0f"),
    ARROW_RIGHT("U+27a1U+fe0f");

    private String codePoints;

    private Emotes(String codePoints){
        this.codePoints = codePoints;
    }

    public String getCodePoints() {
        return codePoints;
    }

    public static Emotes getByCodePoints(String codePoints){
        return Arrays.stream(values()).filter(x->x.getCodePoints().equals(codePoints)).findAny().orElse(null);
    }
}
