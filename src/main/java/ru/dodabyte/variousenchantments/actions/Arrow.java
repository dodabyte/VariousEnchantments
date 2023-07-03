package ru.dodabyte.variousenchantments.actions;

public interface Arrow {
    public void handle();

    public boolean equals(Object object);

    public int hashCode();

    public org.bukkit.entity.Arrow getMinecraftArrow();
}
