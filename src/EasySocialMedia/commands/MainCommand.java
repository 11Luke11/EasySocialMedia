package EasySocialMedia.commands;

import EasySocialMedia.EasySocialMedia;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.List;

public class MainCommand extends BukkitCommand {
    private final EasySocialMedia plugin;

    public MainCommand(EasySocialMedia plugin) {
        super(plugin.getConfigManager().getMainCommandName());
        this.plugin = plugin;
        setAliases(plugin.getConfigManager().getMainCommandAliases());
        setPermission(plugin.getConfigManager().getAdminPermission());
        setPermissionMessage(plugin.getMessageManager().getMessage("no-permission"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return true;

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig(sender);
            return true;
        }

        plugin.getMessageManager().getHelpMessages().forEach(sender::sendMessage);
        return true;
    }

    private void reloadConfig(CommandSender sender) {
        sender.sendMessage(plugin.getMessageManager().getMessage("config-reloading"));
        plugin.getConfigManager().reload();
        plugin.getMessageManager().reload();
        plugin.getCommandManager().unregisterCommands();
        plugin.getCommandManager().registerCommands();
        sender.sendMessage(plugin.getMessageManager().getMessage("config-reloaded"));
    }
}