package org.ip3e.ddos_guard;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ConnectionManager connectionManager;
    private MessageManager messageManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Проверка версии сервера
        String serverVersion = Bukkit.getVersion();
        if (!serverVersion.contains("1.16") && !serverVersion.contains("1.17") &&
                !serverVersion.contains("1.18") && !serverVersion.contains("1.19") &&
                !serverVersion.contains("1.20") && !serverVersion.contains("1.21")) {
            getLogger().warning("Ваша версия сервера не поддерживается. Поддерживаются версии 1.16–1.21.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Инициализация менеджеров
        configManager = new ConfigManager(this);
        messageManager = new MessageManager(this);
        connectionManager = new ConnectionManager(configManager);

        // Регистрация событий
        getServer().getPluginManager().registerEvents(connectionManager, this);

        // Регистрация команд
        getCommand("dg").setExecutor(new DGCommand(this, messageManager, configManager, connectionManager));

        getLogger().info("DDoSGuard plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DDoSGuard plugin has been disabled!");
    }
}