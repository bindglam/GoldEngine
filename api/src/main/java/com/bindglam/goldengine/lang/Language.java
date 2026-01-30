package com.bindglam.goldengine.lang;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Language {
    private final String name;
    private final Map<String, LanguageComponent> map = new HashMap<>();

    public Language(String name) {
        this.name = name;
    }

    public void load(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for(String key : config.getKeys(false)) {
            this.map.put(key, new LanguageComponent(Objects.requireNonNull(config.getString(key))));
        }
    }

    public @NotNull Component get(String key, Object... args) {
        LanguageComponent langComp = this.map.get(key);
        if(langComp != null)
            return langComp.component(args);
        return Component.text(key);
    }

    public String getName() {
        return name;
    }
}
