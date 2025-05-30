package bot.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import bot.core.BotConfiguration;
import bot.service.BotService;
import bot.service.BotServiceInfo;
import okio.Path;

@BotServiceInfo
public class PlatformService extends BotService {

    private ObjectMapper mapper;

	public PlatformService() {
        this.mapper = new ObjectMapper();
    }

    public File getMainDirectory() {
        File directory = Path.get(System.getProperty("user.home")).resolve("."+this.getBot().getName()).toFile();
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

    public File createAuthFile() {
        try (Scanner scanner = new Scanner(System.in)) {
            BotConfiguration configuration = new BotConfiguration();
            configuration.setDiscordToken("TOKEN");
            configuration.setOwnerId(0L);
            configuration.setDatabaseUser("test");
            configuration.setDatabasePassword("test");
            configuration.setDatabaseDriver("org.mariadb.jdbc.Driver");
            configuration.setDatabaseUrl("jdbc:mariadb://localhost/test");

            try {
                File authFile = getAuthFile();
                authFile.createNewFile();
                mapper.writeValue(authFile, configuration);
                return authFile;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public File getAuthFile() {
        File file = Path.get(getConfigDirectory()).resolve("auth.json").toFile();
        return file;
    }

    public File loadAuthFile(){
        File authFile = getAuthFile();
        if(authFile.exists())
            return authFile;
        return createAuthFile();
    }
    
    public BotConfiguration getBotConfiguration() {
        File authFile = loadAuthFile();
        try (InputStream in = new FileInputStream(authFile)) {
            return new ObjectMapper().readValue(in, BotConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
