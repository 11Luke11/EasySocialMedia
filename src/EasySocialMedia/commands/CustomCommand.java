package EasySocialMedia.commands;

import EasySocialMedia.EasySocialMedia;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class CustomCommand extends BukkitCommand {
    private final EasySocialMedia plugin;
    private final String message;

    public CustomCommand(EasySocialMedia plugin, String name, String message) {
        super(name);
        this.plugin = plugin;
        this.message = message;
        setPermission(plugin.getConfigManager().getUsePermission());
        setPermissionMessage(plugin.getMessageManager().getMessage("no-permission"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return true;
        sender.sendMessage(plugin.getMessageManager().format(message));
        return true;
    }
}