package me.yuuthedev.fancyswords.skills;

import me.yuuthedev.fancyswords.Skill;
import me.yuuthedev.fancyswords.Skills;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class GroundPush extends Skill {
    private Plugin plugin = Skills.getPlugin(Skills.class);
    private FileConfiguration config = plugin.getConfig();

    public GroundPush() {
        setName("Ground Push");
        setCooldown(plugin.getConfig().getInt("skills.ground_push.cooldown"));
        setType("Air");
        setData(config.getInt("skills.ground_push.modeldata"));
    }

    @Override
    public void execute(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() != Action.RIGHT_CLICK_AIR) return;

        /*Location p1 = player.getLocation().add(1, 0, 1);
        Location p2 = player.getLocation().subtract(1, 0, 1);

        List<Location> locations = new ArrayList<>();

        for(double x = p1.getX(); x <= p2.getX(); x++){
            for(double y = p1.getY(); y <= p2.getY(); y++){
                for(double z = p1.getZ(); z <= p2.getZ(); z++){
                    locations.add(new Location(p1.getWorld(),x,y,z));
                }
            }
        }

        for(Location l : locations) {
            l.getWorld().spawnParticle(Particle.LEGACY_BLOCK_CRACK, l,100);
        }*/

        player.getWorld().spawnParticle(Particle.valueOf(config.getString("skills.ground_push.particle")),player.getLocation(),10,2,0.1,2);

        for(Entity e : player.getNearbyEntities(2,2,2)) {
            if(e instanceof LivingEntity) {
                ((LivingEntity) e).damage(4);
            }
            Vector vector = e.getLocation().toVector().normalize();
            vector.setY(config.getDouble("skills.ground_push.push_force"));
            if(config.getBoolean("skills.ground_push.only_upwards")) {vector.setX(0); vector.setZ(0);}
            e.setVelocity(vector);
        }
    }

    @Override
    public void execute(PlayerInteractEntityEvent event) { }
}
