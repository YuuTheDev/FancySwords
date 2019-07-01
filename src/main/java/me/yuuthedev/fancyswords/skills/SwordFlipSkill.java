package me.yuuthedev.fancyswords.skills;

import me.yuuthedev.fancyswords.Util;
import me.yuuthedev.fancyswords.Skill;
import me.yuuthedev.fancyswords.Skills;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class SwordFlipSkill extends Skill {
    private Plugin plugin = Skills.getPlugin(Skills.class);
    private FileConfiguration config = plugin.getConfig();
    private Util util = new Util();

    public SwordFlipSkill() {
        setName("Sword Flip");
        setCooldown(config.getInt("skills.sword_flip.cooldown"));
        setType("Entity");
        setData(config.getInt("skills.sword_flip.modeldata"));
    }

    @Override
    public void execute(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        Location loc = player.getLocation();

        if(entity instanceof Player && !((Player) entity).canSee(player)) return;

        Vector vector = entity.getLocation().getDirection();
        vector.setY(0);
        vector.normalize().multiply(-(config.getInt("skills.sword_flip.teleport_away")));

        Location teleportLoc = entity.getLocation().add(vector);

        Vector direction = new Vector();
        direction.setX(loc.add(0,1.5,0).getX()-teleportLoc.add(0,1.5,0).getX());
        direction.setY(loc.add(0,1.5,0).getY()-teleportLoc.add(0,1.5,0).getY());
        direction.setZ(loc.add(0,1.5,0).getZ()-teleportLoc.add(0,1.5,0).getZ());

        teleportLoc.setDirection(direction);

        if(teleportLoc.getBlock().getType() == Material.AIR) {
            player.teleport(teleportLoc);
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("skills.sword_flip.sound")), 20, 20);
        }
        else {
            if(teleportLoc.add(0,1,0).getBlock().getType() == Material.AIR) {
                player.teleport(teleportLoc.add(0,1,0));
                player.playSound(player.getLocation(), Sound.valueOf(config.getString("skills.sword_flip.sound")), 20, 20);
            }
            else {
                util.sendActionbar(player, ChatColor.RED + "Rakibin arkası dolu! Skill başarısız oldu.");
                return;
            }
        }
    }

    @Override
    public void execute(PlayerInteractEvent e) {}
}
