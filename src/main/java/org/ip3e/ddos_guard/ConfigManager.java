package org.ip3e.ddos_guard;


import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final Main plugin;
    private int maxConnectionsPerIp;
    private int statsTime;
    private int cleanupInterval;
    private MessageManager messageManager;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        this.messageManager = new MessageManager(plugin);
        loadConfig();
    }

    // Загрузка конфигурации
    private void loadConfig() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        maxConnectionsPerIp = config.getInt("max-connections-per-ip", 5);
        statsTime = config.getInt("stats-time", 5);
        cleanupInterval = config.getInt("cleanup-interval", 5);
    }

    // Перезагрузка конфигурации
    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
        messageManager.reloadMessages();
    }

    // Геттеры для настроек
    public int getMaxConnectionsPerIp() {
        return maxConnectionsPerIp;
    }

    public int getStatsTime() {
        return statsTime;
    }

    public int getCleanupInterval() {
        return cleanupInterval;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}