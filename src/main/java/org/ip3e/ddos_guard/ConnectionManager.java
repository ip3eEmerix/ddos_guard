package org.ip3e.ddos_guard;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements Listener {

    private final Map<String, Integer> ipConnections = new HashMap<>();
    private final ConfigManager configManager;

    public ConnectionManager(ConfigManager configManager) {
        this.configManager = configManager;
        startCleanupTask();
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        String ipAddress = event.getAddress().getHostAddress();

        int connections = ipConnections.getOrDefault(ipAddress, 0);

        if (connections >= configManager.getMaxConnectionsPerIp()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, configManager.getMessageManager().getMessage("too-many-connections"));
            return;
        }

        ipConnections.put(ipAddress, connections + 1);
    }

    // Запуск задачи очистки старых записей
    private void startCleanupTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Bukkit.getPluginManager().getPlugin("DDoSGuard"), () -> {
            ipConnections.clear();
            Bukkit.getLogger().info(configManager.getMessageManager().getMessage("cleared-connections"));
        }, configManager.getCleanupInterval() * 1200L, configManager.getCleanupInterval() * 1200L);
    }

    // Отправка статистики подключений
    public void sendConnectionInfo(CommandSender sender) {
        for (Map.Entry<String, Integer> entry : ipConnections.entrySet()) {
            sender.sendMessage(configManager.getMessageManager().getMessage("info-entry")
                    .replace("{ip}", entry.getKey())
                    .replace("{connections}", String.valueOf(entry.getValue())));
        }
    }
}