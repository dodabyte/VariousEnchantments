package ru.dodabyte.variousenchantments.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import ru.dodabyte.variousenchantments.enchantments.VariousEnchantmentActions;
import ru.dodabyte.variousenchantments.scheduler.Timer;

import java.util.Map;
import java.util.UUID;

public class CheckTimers extends BukkitRunnable {
    @Override
    public void run() {
        if (VariousEnchantmentActions.thunderclapTimerMap != null && VariousEnchantmentActions.thunderclapTimerMap.size() > 0) {
            for (Map.Entry<UUID, Timer> cooldownEntry : VariousEnchantmentActions.thunderclapTimerMap.entrySet()) {
                if (cooldownEntry != null && cooldownEntry.getValue() != null &&
                        cooldownEntry.getValue().getScheduler() != null &&
                        cooldownEntry.getValue().getScheduler().isShutdown()) {
                    VariousEnchantmentActions.thunderclapTimerMap.remove(cooldownEntry.getKey());
                }
            }
        }
    }
}
