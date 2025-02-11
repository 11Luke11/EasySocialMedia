package EasySocialMedia.config;

import EasySocialMedia.EasySocialMedia;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.*;

public class ConfigManager {
    private final EasySocialMedia plugin;
    private FileConfiguration config;

    public ConfigManager(EasySocialMedia plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        reload();
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }

    // Main command configuration
    public String getMainCommandName() {
        return config.getString("settings.main-command.name", "esm");
    }

    public List<String> getMainCommandAliases() {
        return config.getStringList("settings.main-command.aliases");
    }

    public boolean isMainCommandEnabled() {
        return config.getBoolean("settings.main-command.enabled", true);
    }

    // Social command configuration
    public String getSocialCommandName() {
        return config.getString("settings.social-command.name", "social");
    }

    public List<String> getSocialCommandAliases() {
        return config.getStringList("settings.social-command.aliases");
    }

    public boolean isSocialCommandEnabled() {
        return config.getBoolean("settings.social-command.enabled", true);
    }

    // Permissions
    public String getAdminPermission() {
        return config.getString("settings.permissions.admin", "easysocialmedia.admin");
    }

    public String getUsePermission() {
        return config.getString("settings.permissions.use", "easysocialmedia.use");
    }

    // Custom commands
    public Map<String, String> getCustomCommands() {
        Map<String, String> commands = new HashMap<>();
        ConfigurationSection section = config.getConfigurationSection("commands");

        if (section != null) {
            for (String key : section.getKeys(false)) {
                String message = section.getString(key + ".message");
                if (message != null && !message.isEmpty()) {
                    commands.put(key.toLowerCase(), message);
                }
            }
        } else {
            plugin.getLogger().warning("[EasySocialMedia] Missing 'commands' section in config.yml!");
        }
        return commands;
    }

    public boolean isUpdateCheckerEnabled() {
        return config.getBoolean("settings.update-checker.enabled", true);
    }
}