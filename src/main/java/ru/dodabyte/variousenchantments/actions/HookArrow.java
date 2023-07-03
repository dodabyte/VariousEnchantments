package ru.dodabyte.variousenchantments.actions;

import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.Objects;

public class HookArrow implements Arrow {
    private final org.bukkit.entity.Arrow arrow;
    private final Location startLocation;
    private final int distance;

    public HookArrow(org.bukkit.entity.Arrow arrow, Location startLocation, int distance) {
        this.arrow = arrow;
        this.startLocation = startLocation;
        this.distance = distance;
    }

    @Override
    public void handle() {
        if (arrow != null) {
            arrow.getWorld().spawnParticle(Particle.CRIT, arrow.getLocation(), 1);
            if (startLocation.distance(arrow.getLocation()) >= distance) {
                arrow.remove();
                CustomArrows.getArrowMap().remove(arrow.getUniqueId());
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        HookArrow that = (HookArrow) object;
        return Objects.equals(arrow.getUniqueId(), that.arrow.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrow.getUniqueId());
    }

    @Override
    public org.bukkit.entity.Arrow getMinecraftArrow() {
        return arrow;
    }
}
