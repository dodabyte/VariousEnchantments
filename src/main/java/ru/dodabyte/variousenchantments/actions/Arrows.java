package ru.dodabyte.variousenchantments.actions;

import org.bukkit.Bukkit;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Arrows {
    private static final Map<UUID, TeleportArrow> enderArrowMap = new HashMap<>();

    public static void schedulerTeleportArrow() {
        Bukkit.getScheduler().runTaskTimer(VariousEnchantmentsMain.getProvidingPlugin(VariousEnchantmentsMain.class), (task) -> {
            for (TeleportArrow arrow : enderArrowMap.values())
                arrow.teleportToArrow();
        }, 0L, 1L);
    }

    public static Map<UUID, TeleportArrow> getEnderArrowMap() { return enderArrowMap; }
}
