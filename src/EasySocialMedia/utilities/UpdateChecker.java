package EasySocialMedia.utilities;

import EasySocialMedia.EasySocialMedia;
import org.bukkit.Bukkit;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker {
    private final EasySocialMedia plugin;
    private final int resourceId;

    public UpdateChecker(EasySocialMedia plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void checkForUpdates() {
        CompletableFuture.runAsync(() -> {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(
                        "https://api.spigotmc.org/legacy/update.php?resource=" + resourceId
                ).openConnection();

                conn.setRequestMethod("GET");
                try(Scanner scanner = new Scanner(conn.getInputStream())) {
                    String latest = scanner.nextLine();
                    String current = plugin.getDescription().getVersion();

                    if(!latest.equals(current)) {
                        Bukkit.getScheduler().runTask(plugin, () ->
                                plugin.getLogger().info("[EasySocialMedia] Update available: " + latest)
                        );
                    }
                }
            } catch(Exception e) {
                plugin.getLogger().warning("[EasySocialMedia] Update check failed: " + e.getMessage());
            }
        });
    }
}