package me.yuuthedev.fancyswords;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Skills extends JavaPlugin implements Listener {
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nFancySwords aktif\n\n");

        getCommand("bune").setExecutor(new Commands());
        getCommand("fsreload").setExecutor(new Commands());

        getServer().getPluginManager().registerEvents(new SkillHandler(), this);
        getServer().getPluginManager().registerEvents(this, this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        lowGravity();
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) { //Velocity debug lines
        Item entity = event.getItemDrop();
        Player player = event.getPlayer();
        if(!player.getName().equals("YuuTheDev")) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(entity.isOnGround()) cancel();
                player.sendMessage(entity.getVelocity().toString());
            }
        }.runTaskTimer(this, 0, 1);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) { //Particle test lines for YuuTheDev
        Player player = event.getPlayer();
        World world = player.getWorld();

        if(event.getAction() != Action.LEFT_CLICK_AIR) return;
        if(!player.getName().equalsIgnoreCase("YuuTheDev")) return;

        Location loc = player.getEyeLocation();
        Location loc2 = loc.add(loc.getDirection().normalize().multiply(4));

        player.sendMessage(loc.toVector().toString() + "\n" + loc2.toVector().toString());
        Vector vector = loc2.toVector().subtract(loc.toVector());

        Vector effectVector = vector.clone().normalize();
        double t = 0;
        while(effectVector.length() < vector.length()){
            double x = effectVector.getX() * t;
            double y = effectVector.getY() * t;
            double z = effectVector.getZ() * t;
            loc2.add(x,y,z);
            world.spawnParticle(Particle.FIREWORKS_SPARK, loc2, 1);
            loc2.subtract(x,y,z);
        }
    }

    public void lowGravity() { //Low Gravity lines
        new BukkitRunnable() {
            @Override
            public void run() {
                if(getConfig().getBoolean("lowgravity")) {
                    for(World world : Bukkit.getWorlds()) {
                        for(Entity entity : world.getEntities()) {
                            if(entity.isOnGround()) continue;
                            Vector vector = entity.getLocation().getDirection();
                            vector.setX(0);
                            vector.setZ(0);
                            vector.setY(getConfig().getLong("lowgravity_pushvalue"));
                            entity.setVelocity(vector);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0 , getConfig().getLong("lowgravity_pushdelay"));
    }

    public void onDisable() {}
}
