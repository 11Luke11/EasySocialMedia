package EasySocialMedia.commands;

import EasySocialMedia.EasySocialMedia;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class SocialCommand extends BukkitCommand {
    private final EasySocialMedia plugin;

    public SocialCommand(EasySocialMedia plugin) {
        super(plugin.getConfigManager().getSocialCommandName());
        this.plugin = plugin;
        setAliases(plugin.getConfigManager().getSocialCommandAliases());
        setPermission(plugin.getConfigManager().getUsePermission());
        setPermissionMessage(plugin.getMessageManager().getMessage("no-permission"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return true;
        plugin.getMessageManager().getSocialMediaMessages().forEach(sender::sendMessage);
        return true;
    }
}