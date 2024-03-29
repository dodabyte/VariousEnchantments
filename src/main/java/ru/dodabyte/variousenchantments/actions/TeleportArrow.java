package ru.dodabyte.variousenchantments.actions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Objects;

public class TeleportArrow implements Arrow {
    private final org.bukkit.entity.Arrow arrow;
    private final LivingEntity entity;

    public TeleportArrow(org.bukkit.entity.Arrow arrow, LivingEntity entity) {
        this.arrow = arrow;
        this.entity = entity;
    }

    @Override
    public void handle() {
        if (arrow != null) {
            entity.setGliding(true);
            Location arrowLocation = arrow.getLocation();
            arrowLocation.setYaw(arrowLocation.getYaw() * (-1));
            arrowLocation.setPitch(arrowLocation.getPitch() * (-1));
            entity.teleport(arrowLocation);
        }
    }

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

    @Override
    public org.bukkit.entity.Arrow getMinecraftArrow() {
        return arrow;
    }
}
