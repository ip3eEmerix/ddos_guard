package org.ip3e.ddos_guard;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageManager {

    private File messagesFile;
    private FileConfiguration messagesConfig;

    public MessageManager(Main plugin) {
        loadMessages(plugin);
    }

    // Загрузка messages.yml
    private void loadMessages(Main plugin) {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    // Перезагрузка сообщений
    public void reloadMessages() {
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    // Получение сообщения по ключу
    public String getMessage(String key) {
        String message = messagesConfig.getString(key, "§cСообщение не найдено: " + key);
        return message.replace("&", "§"); // Заменяем & на § для цветов
    }
}