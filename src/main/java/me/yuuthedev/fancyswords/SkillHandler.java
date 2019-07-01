package me.yuuthedev.fancyswords;

import me.yuuthedev.fancyswords.skills.GroundPush;
import me.yuuthedev.fancyswords.skills.SwordFlipSkill;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillHandler implements Listener {
    private Map<Integer, Skill> skills = new HashMap<>();
    private Map<UUID, Long> cooldown = new HashMap<>();

    private Util util = new Util();

    private Plugin plugin = Skills.getPlugin(Skills.class);

    public SkillHandler() {
        skills.put(plugin.getConfig().getInt("skills.sword_flip.modeldata"), new SwordFlipSkill());
        skills.put(plugin.getConfig().getInt("skills.ground_push.modeldata"), new GroundPush());
    }

    @EventHandler
    public void onExecute(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if(player.getInventory().getItemInMainHand() == null) return;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if(!itemInHand.hasItemMeta()) return;
        if(!itemInHand.getItemMeta().hasCustomModelData()) return;

        Skill skill = getSkillFromData(itemInHand.getItemMeta().getCustomModelData());
        if(skill == null) return;
        if(!skill.getType().equals("Entity")) return;

        if(cooldown.containsKey(player.getUniqueId()) && System.currentTimeMillis() < cooldown.get(player.getUniqueId())) {
            player.playSound(player.getLocation(), Sound.BLOCK_SAND_STEP, 5 ,1);
        }
        else {
            skill.execute(event);
            player.sendMessage(ChatColor.GREEN + skill.getName() + ChatColor.GRAY + " skillini kullandın.");
            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (1000 * skill.getCooldown()));
        }
    }

    @EventHandler
    public void onExecute(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(player.getInventory().getItemInMainHand() == null) return;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if(!itemInHand.hasItemMeta()) return;
        if(!itemInHand.getItemMeta().hasCustomModelData()) return;

        Skill skill = getSkillFromData(itemInHand.getItemMeta().getCustomModelData());
        if(skill == null) return;
        if(!skill.getType().equals("Air")) return;

        if(cooldown.containsKey(player.getUniqueId()) && System.currentTimeMillis() < cooldown.get(player.getUniqueId())) {
            player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation().add(player.getLocation().getDirection().normalize()), 1);
        }
        else {
            skill.execute(event);
            player.sendMessage(ChatColor.GREEN + skill.getName() + ChatColor.GRAY + " skillini kullandın.");
            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (1000 * skill.getCooldown()));
        }
    }

    public Skill getSkillFromData(Integer data) {
        return skills.get(data);
    }

    public Map<Integer, Skill> getSkills() {
        return skills;
    }
}
