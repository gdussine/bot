package bot.platform;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import bot.core.BotConfiguration;
import bot.service.core.AbstractBotService;
import bot.service.core.BotService;
import okio.Path;

@BotService
public class PlatformService extends AbstractBotService {

    public PlatformService() {
    }

    public File getMainDirectory() {
        return Path.get(System.getProperty("user.home")).resolve("."+this.getBot().getName()).toFile();
    }

    public File getConfigDirectory() {
        return Path.get(getMainDirectory()).resolve("config").toFile();
    }

    public File getOutDirectory() {
        return Path.get(getMainDirectory()).resolve("out").toFile();
    }

    public File createAuthFile() {
        try (Scanner scanner = new Scanner(System.in)) {
            BotConfiguration configuration = new BotConfiguration();
            System.out.println("DiscordToken: ");
            configuration.setDiscordToken(scanner.nextLine());
            System.out.println("OwnerId: ");
            configuration.setOwnerId(scanner.nextLong());
            System.out.println("DatabaseUser: ");
            configuration.setDatabaseUser(scanner.nextLine());
            System.out.println("DatabasePassword");
            configuration.setDatabasePassword(scanner.nextLine());
            configuration.setDatabaseDriver("org.mariadb.jdbc.Driver");
            configuration.setDatabaseUrl("jdbc:mariadb://localhost/test");
            ObjectMapper mapper = new ObjectMapper();
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
        return Path.get(getConfigDirectory()).resolve("auth.json").toFile();
    }

    public File loadAuthFile(){
        File authFile = getAuthFile();
        if(authFile.exists())
            return authFile;
        return createAuthFile();
    }

}
