package EasySocialMedia.utilities;

import org.bukkit.ChatColor;
import java.util.Map;

public class ChatUtils {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String format(String text, Map<String, String> replacements) {
        String formatted = color(text);
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            formatted = formatted.replace(entry.getKey(), entry.getValue());
        }
        return formatted;
    }
}