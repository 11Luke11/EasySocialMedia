package EasySocialMedia.commands;

import EasySocialMedia.EasySocialMedia;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.*;

public class CommandManager {
    private final EasySocialMedia plugin;
    private final List<BukkitCommand> commands = new ArrayList<>();
    private SimpleCommandMap commandMap;

    public CommandManager(EasySocialMedia plugin) {
        this.plugin = plugin;
        setupCommandMap();
    }

    private void setupCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
        } catch (Exception e) {
            plugin.getLogger().severe("[EasySocialMedia] Failed to get CommandMap: " + e.getMessage());
        }
    }

    public void registerCommands() {
        unregisterCommands();

        if (plugin.getConfigManager().isMainCommandEnabled()) {
            commands.add(new MainCommand(plugin));
        }

        if (plugin.getConfigManager().isSocialCommandEnabled()) {
            commands.add(new SocialCommand(plugin));
        }

        Map<String, String> customCommands = plugin.getConfigManager().getCustomCommands();
        if (!customCommands.isEmpty()) {
            for (Map.Entry<String, String> entry : customCommands.entrySet()) {
                commands.add(new CustomCommand(plugin, entry.getKey(), entry.getValue()));
            }
        }

        commands.forEach(this::registerCommand);
    }

    private void registerCommand(BukkitCommand command) {
        if (commandMap != null) {
            commandMap.register(plugin.getName().toLowerCase(), command);
        }
    }

    public void unregisterCommands() {
        if (commandMap == null) return;

        new ArrayList<>(commands).forEach(command -> {
            try {
                Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                @SuppressWarnings("unchecked")
                Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);

                command.getAliases().forEach(knownCommands::remove);
                knownCommands.remove(command.getName());

                commands.remove(command);
            } catch (Exception e) {
                plugin.getLogger().warning("[EasySocialMedia] Failed to unregister command: " + command.getName());
            }
        });
    }
}