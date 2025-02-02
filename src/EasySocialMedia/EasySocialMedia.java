package EasySocialMedia;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EasySocialMedia extends JavaPlugin {

    private List<Command> registeredCommands = new ArrayList<>();
    private File messagesFile;
    private FileConfiguration messages;
    private CommandMap commandMap;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupMessages();
        setupCommandMap();
        registerAllCommands();
        getLogger().info(getMessage("plugin-enabled"));
    }

    private void setupMessages() {
        messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) saveResource("messages.yml", false);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private void setupCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());
        } catch (Exception e) {
            getLogger().severe("Failed to get CommandMap: " + e.getMessage());
        }
    }

    private void registerAllCommands() {
        unregisterAllCommands();
        registerCustomCommands();

        if (getConfig().getBoolean("settings.main-command.enabled", true)) {
            Command mainCmd = new MainCommand();
            registeredCommands.add(mainCmd);
            registerCommand(mainCmd);
        }

        if (getConfig().getBoolean("settings.social-command.enabled", true)) {
            Command socialCmd = new SocialCommand();
            registeredCommands.add(socialCmd);
            registerCommand(socialCmd);
        }
    }

    private void registerCustomCommands() {
        ConfigurationSection commandsSection = getConfig().getConfigurationSection("commands");
        if (commandsSection == null) return;

        for (String name : commandsSection.getKeys(false)) {
            String message = commandsSection.getString(name + ".message");
            if (message == null) continue;

            Command cmd = new CustomCommand(name, message);
            registeredCommands.add(cmd);
            registerCommand(cmd);
        }
    }

    private void registerCommand(Command command) {
        if (commandMap != null) commandMap.register("easysocialmedia", command);
    }

    private void unregisterAllCommands() {
        registeredCommands.forEach(cmd -> cmd.unregister(commandMap));
        registeredCommands.clear();
    }

    private String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&',
                messages.getString("messages." + path, "&cMissing message: " + path));
    }

    private class MainCommand extends Command {
        MainCommand() {
            super(getConfig().getString("settings.main-command.name", "esm"));
            setAliases(getConfig().getStringList("settings.main-command.aliases"));
            setPermission(getConfig().getString("settings.permissions.admin"));
            setPermissionMessage(getMessage("no-permission"));
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            if (!testPermissionSilent(sender)) {
                sender.sendMessage(getMessage("no-permission"));
                return true;
            }

            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                reloadPlugin();
                sender.sendMessage(getMessage("config-reloaded"));
                return true;
            }

            sender.sendMessage(getMessage("no-permission"));
            return true;
        }
    }

    private class SocialCommand extends Command {
        SocialCommand() {
            super(getConfig().getString("settings.social-command.name", "social"));
            setAliases(getConfig().getStringList("settings.social-command.aliases"));
            setPermission(getConfig().getString("settings.permissions.use"));
            setPermissionMessage(getMessage("no-permission"));
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            if (!testPermissionSilent(sender)) {
                sender.sendMessage(getMessage("no-permission"));
                return true;
            }
            sendSocialMedia(sender);
            return true;
        }
    }

    private class CustomCommand extends Command {
        private final String message;

        CustomCommand(String name, String message) {
            super(name);
            this.message = ChatColor.translateAlternateColorCodes('&', message);
            setPermission(getConfig().getString("settings.permissions.use"));
            setPermissionMessage(getMessage("no-permission"));
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            if (!testPermissionSilent(sender)) {
                sender.sendMessage(getMessage("no-permission"));
                return true;
            }
            sender.sendMessage(message);
            return true;
        }
    }

    private void sendHelp(CommandSender sender) {
        String mainCmd = getConfig().getString("settings.main-command.name");
        List<String> help = messages.getStringList("messages.help")
                .stream()
                .map(line -> line.replace("{main}", mainCmd))
                .collect(Collectors.toList());

        help.forEach(line -> sender.sendMessage(color(line)));
    }

    private void sendSocialMedia(CommandSender sender) {
        String socialCmd = getConfig().getString("settings.social-command.name");
        List<String> social = messages.getStringList("messages.socialmedia")
                .stream()
                .map(line -> line.replace("{commands}", getCommandList())
                        .replace("{social}", socialCmd))
                .collect(Collectors.toList());

        social.forEach(line -> sender.sendMessage(color(line)));
    }

    private String getCommandList() {
        ConfigurationSection commands = getConfig().getConfigurationSection("commands");
        if (commands == null) return "";

        return commands.getKeys(false).stream()
                .map(cmd -> "/" + cmd)
                .collect(Collectors.joining(color(messages.getString("messages.command-list-separator"))));
    }

    private String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private void reloadPlugin() {
        reloadConfig();
        setupMessages();
        registerAllCommands();
    }
}