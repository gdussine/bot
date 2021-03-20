package com.github.gdussine.bot.core;

import javax.security.auth.login.LoginException;

import com.github.gdussine.bot.utils.BotExecutionListener;
import net.dv8tion.jda.api.JDA;

import net.dv8tion.jda.api.JDABuilder;

public class Bot {

	protected JDABuilder jdaBuilder;
	protected BotConfiguration config;
	protected BotExecutionListener executionListener;
	protected JDA jda;
	protected static Bot instance;

	public Bot(BotConfiguration config) {
		executionListener = new BotExecutionListener(this);
		jdaBuilder = JDABuilder.createDefault(config.getDiscordToken()).addEventListeners(executionListener);
		this.config = config;
		instance = this;
	}

	public void start() throws LoginException, InterruptedException {
		jda = jdaBuilder.build().awaitReady();
	}

	public static Bot getInstance() {
		return instance;
	}

	public BotConfiguration getConfig() {
		return config;
	}

	public void stop() {
		jda.shutdown();
	}
}
