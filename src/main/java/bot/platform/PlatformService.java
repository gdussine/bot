
package bot.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import bot.api.framework.TemplateBotService;
import bot.core.BotConfiguration;
import okio.Path;

public class PlatformService extends TemplateBotService {

	private ObjectMapper mapper;

	private static final String AUTH_FILENAME = "auth.json";

	public PlatformService() {
		this.mapper = new ObjectMapper();
	}

	public File getMainDirectory() {
		File directory = Path.get(System.getProperty("user.home")).resolve("." + this.getBot().getName()).toFile();
		directory.mkdir();
		return directory;
	}

	public File getConfigDirectory() {
		File directory = Path.get(getMainDirectory()).resolve("config").toFile();
		directory.mkdir();
		return directory;
	}

	public File getOutDirectory() {
		File directory = Path.get(getMainDirectory()).resolve("out").toFile();
		directory.mkdir();
		return directory;
	}

	public File getConfigFile(String fileName) {
		File file = Path.get(getConfigDirectory()).resolve(fileName).toFile();
		return file;
	}

	public <T extends PlatformConfiguration> File createConfigFile(String configFileName, Class<T> configType) {
		try {
			File configFile = getConfigFile(configFileName);
			configFile.createNewFile();
			mapper.writeValue(configFile,  getDefaultConfiguration(configType));
			return configFile;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public <T extends PlatformConfiguration> T getDefaultConfiguration(Class<T> configClass) {
		try {
			T config = configClass.getConstructor().newInstance();
			config.setDefaultConfiguration();
			return config;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T extends PlatformConfiguration> T getPlatformConfiguration(String fileName, Class<T> configType) {
		File configFile = getConfigFile(fileName);
		if (!configFile.exists()) {
			configFile = createConfigFile(fileName, configType);
		}
		try (InputStream in = new FileInputStream(configFile)) {
			return new ObjectMapper().readValue(in, configType);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	public BotConfiguration getBotConfiguration() {

		return getPlatformConfiguration(AUTH_FILENAME, BotConfiguration.class);
	}

}
