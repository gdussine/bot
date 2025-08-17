
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

    public File createConfigFile(String configFileName, Object configObject) {
        try {
            File configFile = getConfigFile(configFileName);
            configFile.createNewFile();
            mapper.writeValue(configFile, configObject);
            return configFile;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public File loadConfigFile(String fileName, Object defaultObject) {
        File file = getConfigFile(fileName);
        if (file.exists()) {
            return file;
        }
        return createConfigFile(fileName, defaultObject);
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

    public <T extends PlatformConfiguration> T getPlatformConfiguration(String filename, Class<T> configClass) {
        File configFile = loadConfigFile(filename, configClass);
        try (InputStream in = new FileInputStream(configFile)) {
            return new ObjectMapper().readValue(in, configClass);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public BotConfiguration getBotConfiguration() {
        return getPlatformConfiguration(AUTH_FILENAME, BotConfiguration.class);
    }

}
