package EasySocialMedia;

import EasySocialMedia.commands.CommandManager;
import EasySocialMedia.config.ConfigManager;
import EasySocialMedia.config.MessageManager;
import EasySocialMedia.utilities.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EasySocialMedia extends JavaPlugin {
    private ConfigManager configManager;
    private MessageManager messageManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(this);
        this.commandManager = new CommandManager(this);

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            configManager.loadConfig();
            messageManager.loadMessages();

            Bukkit.getScheduler().runTask(this, () -> {
                commandManager.registerCommands();
                getLogger().info(messageManager.getMessage("plugin-enabled"));

                if(configManager.isUpdateCheckerEnabled()) {
                    new UpdateChecker(this, 122333).checkForUpdates();
                }
            });
        });
    }

    @Override
    public void onDisable() {
        commandManager.unregisterCommands();
    }

    public ConfigManager getConfigManager() { return configManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public CommandManager getCommandManager() { return commandManager; }
}
