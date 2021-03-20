package com.github.gdussine.bot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gdussine.bot.core.Bot;
import com.github.gdussine.bot.core.BotConfiguration;

import java.io.InputStream;
import java.lang.reflect.Constructor;

public class BotBuilder<B extends Bot, C extends BotConfiguration> {

    private final ObjectMapper mapper;

    private Class<B> botType;
    private Class<C> configType;

    public BotBuilder(Class<B> botType, Class<C> configType){
        this.mapper = new ObjectMapper();
        this.botType = botType;
        this.configType = configType;
    }

    public B build(String path){
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
            C config = mapper.readValue(is, configType);
            Constructor<B> botConstructor = botType.getConstructor(configType);
            return botConstructor.newInstance(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
 