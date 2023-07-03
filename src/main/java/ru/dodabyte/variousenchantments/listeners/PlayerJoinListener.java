package ru.dodabyte.variousenchantments.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.dodabyte.variousenchantments.utils.config.Configurations;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Configurations.getLanguage().checkItemsLocalization(event.getPlayer());
    }
}
