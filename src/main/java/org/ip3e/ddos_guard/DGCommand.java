package org.ip3e.ddos_guard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DGCommand implements org.bukkit.command.CommandExecutor {

    private final Main plugin;
    private final MessageManager messageManager;
    private final ConfigManager configManager;
    private final ConnectionManager connectionManager;

    public DGCommand(Main plugin, MessageManager messageManager, ConfigManager configManager, ConnectionManager connectionManager) {
        this.plugin = plugin;
        this.messageManager = messageManager;
        this.configManager = configManager;
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(messageManager.getMessage("unknown-command"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                sender.sendMessage(messageManager.getMessage("help-message").replace("{stats_time}", String.valueOf(configManager.getStatsTime())));
                return true;

            case "reload":
                configManager.reloadConfig();
                messageManager.reloadMessages();
                sender.sendMessage(messageManager.getMessage("reload-success"));
                return true;

            case "info":
                sender.sendMessage(messageManager.getMessage("info-header").replace("{stats_time}", String.valueOf(configManager.getStatsTime())));
                connectionManager.sendConnectionInfo(sender);
                return true;

            default:
                sender.sendMessage(messageManager.getMessage("unknown-command"));
                return true;
        }
    }
}