package EasySocialMedia.config;

import EasySocialMedia.EasySocialMedia;
import EasySocialMedia.utilities.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MessageManager {
    private final EasySocialMedia plugin;
    private FileConfiguration messages;

    public MessageManager(EasySocialMedia plugin) {
        this.plugin = plugin;
    }

    public void loadMessages() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) plugin.saveResource("messages.yml", false);
        reload();
    }

    public void reload() {
        messages = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
    }

    public String getMessage(String path) {
        String message = messages.getString("messages." + path);
        return message != null ?
                ChatUtils.color(message) :
                ChatUtils.color("&cMissing message: " + path);
    }

    public List<String> getHelpMessages() {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("{main}", plugin.getConfigManager().getMainCommandName());
        replacements.put("{social}", plugin.getConfigManager().getSocialCommandName());
        return formatMessages("help", replacements);
    }

    public List<String> getSocialMediaMessages() {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("{commands}", String.join(
                getMessage("command-list-separator"),
                plugin.getConfigManager().getCustomCommands().keySet()
        ));
        return formatMessages("socialmedia", replacements);
    }

    private List<String> formatMessages(String path, Map<String, String> replacements) {
        return messages.getStringList("messages." + path).stream()
                .map(line -> ChatUtils.format(line, replacements))
                .collect(Collectors.toList());
    }

    public String format(String text) {
        return ChatUtils.color(text);
    }
}