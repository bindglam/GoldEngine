package com.bindglam.goldengine.lang;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class LanguageComponent {
    private final String raw;

    public LanguageComponent(String raw) {
        this.raw = raw;
    }

    public Component component(Object... args) {
        int argIdx = 0;

        String result = raw;
        while(result.contains("%s")) {
            result = result.replaceFirst("%s", args[argIdx++].toString());
        }
        return MiniMessage.miniMessage().deserialize(result);
    }
}
