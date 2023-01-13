package ru.dodabyte.variousenchantments.actions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Objects;

public class TeleportArrow {
    private final Entity arrow;

    private final LivingEntity entity;

    public TeleportArrow(Entity arrow, LivingEntity entity) {
        this.arrow = arrow;
        this.entity = entity;
    }

    public void teleportToArrow() {
        if (arrow != null) {
            getEntity().setGliding(true);
            Location arrowLocation = arrow.getLocation();
            arrowLocation.setYaw(arrowLocation.getYaw() * (-1));
            arrowLocation.setPitch(arrowLocation.getPitch() * (-1));
            getEntity().teleport(arrowLocation);
        }
    }

    public LivingEntity getEntity() { return entity; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TeleportArrow that = (TeleportArrow) object;
        return Objects.equals(arrow.getUniqueId(), that.arrow.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrow.getUniqueId());
    }
}
