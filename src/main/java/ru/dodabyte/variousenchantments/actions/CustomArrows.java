package ru.dodabyte.variousenchantments.actions;

import org.bukkit.Bukkit;
import ru.dodabyte.variousenchantments.VariousEnchantmentsMain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomArrows {
    private static final Map<UUID, Arrow> arrowMap = new HashMap<>();

    public static void schedulerArrow() {
        Bukkit.getScheduler().runTaskTimer(VariousEnchantmentsMain.getProvidingPlugin(VariousEnchantmentsMain.class), (task) -> {
            if (arrowMap.size() > 0) {
                for (Arrow arrow : arrowMap.values()) {
                    if (arrow != null) {
                        arrow.handle();
                    }
                }
            }
        }, 0L, 1L);
    }

    public static Map<UUID, Arrow> getArrowMap() { return arrowMap; }
}
