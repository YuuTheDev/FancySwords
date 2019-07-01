package me.yuuthedev.fancyswords;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class Skill {
    private String name;
    private int cooldown;
    private int data;
    private String type;

    public abstract void execute(PlayerInteractEntityEvent e);

    public abstract void execute(PlayerInteractEvent e);

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getData() { return data; }

    public void setData(int data) { this.data = data; }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
