package me.yuuthedev.fancyswords;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {
    private Plugin plugin = Skills.getPlugin(Skills.class);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.isOp()) return false;
            if(cmd.getName().equalsIgnoreCase("bune")) {
                if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {sender.sendMessage(ChatColor.RED + "Görmüyor musun? Elin :/"); return false;}
                player.sendMessage(player.getInventory().getItemInMainHand().toString());
                if(player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    player.sendMessage("Custom Model Data: " + player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData());
                }
                return true;
            }
            if(cmd.getName().equalsIgnoreCase("fsreload")) {
                plugin.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "Config.yml yenilendi");
                return true;
            }
            if(cmd.getName().equalsIgnoreCase("gravity")) {
                if(args.length == 0) { //Farklı argümanlar eklenecek
                    if(plugin.getConfig().getBoolean("lowgravity") == true) {plugin.getConfig().set("lowgravity", false);
                    player.sendMessage("Lowgravity off");}
                    else {plugin.getConfig().set("lowgravity", true); player.sendMessage("Lowgravity on");}
                }
            }
        }
        return false;
    }
}
