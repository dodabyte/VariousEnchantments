package ru.dodabyte.variousenchantments.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.dodabyte.variousenchantments.scheduler.Timer;

import java.util.*;

public class FreezingArrow implements Arrow {
    private final org.bukkit.entity.Arrow arrow;
    private final float radius;
    private final int cooldown;
    private List<Block> blocksList;
    private Timer timer;

    public FreezingArrow(org.bukkit.entity.Arrow arrow, float radius, int cooldown) {
        this.arrow = arrow;
        this.radius = radius;
        this.cooldown = cooldown;
    }

    @Override
    public void handle() {
        if (arrow != null) {
            if (arrow.getLocation().getBlock().getType().equals(Material.WATER)) {
                if (timer == null) {
                    timer = new Timer(cooldown);
                    timer.start();

                    freeze();
                }
            }
            if (arrow.getLocation().getBlock().getType().equals(Material.ICE)) {
                if (timer != null && timer.getScheduler().isShutdown()) {
                    timer = null;

                    unfreeze();

                    if (CustomArrows.getArrowMap() != null &&
                            CustomArrows.getArrowMap().containsKey(arrow.getUniqueId())) {
                        CustomArrows.getArrowMap().remove(arrow.getUniqueId());
                    }
                }
            }
        }
    }

    private void freeze() {
        blocksList = getBlocks(arrow.getLocation(), (int) radius, false, true);
        for (Block block : blocksList) {
            if (block.getType().equals(Material.WATER)) {
                block.setType(Material.ICE);
            }
        }
    }

    private void unfreeze() {
        if (blocksList != null) {
            for (Block block : blocksList) {
                if (block.getType().equals(Material.ICE)) {
                    block.setType(Material.WATER);
                }
            }
        }
    }

    private List<Block> getBlocks(Location center, int radius, boolean hollow, boolean sphere) {
        List<Block> blocks = new ArrayList<>();

        for (Location loc : circle(center, radius, 1, hollow, sphere, 0)) {
            blocks.add(loc.getBlock());
        }

        return blocks;
    }

    private List<Location> circle(Location loc, int radius, int height, boolean hollow, boolean sphere, int plusY) {
        List<Location> circleBlocks = new ArrayList<>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);

                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        Location l = new Location(loc.getWorld(), x, y + plusY, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }

        return circleBlocks;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FreezingArrow that = (FreezingArrow) object;
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
