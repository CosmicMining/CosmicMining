package net.goldmc.cosmicmining.Commands;

import net.goldmc.cosmicmining.Leveling.XpBoosters.XpBooster;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateXpBooster implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender.hasPermission("cosmicmining.xpboostercommand")) {
            String invalid = ChatColor.RED + "Invalid arguments, use /xpbooster <multiplier> <duration>";
            if(args.length == 2) {
                try {
                    double multiplier = Double.parseDouble(args[0]);
                    int minutes = Integer.parseInt(args[1]);
                    if(multiplier > 0 && minutes > 0) {
                        XpBooster xpBooster = new XpBooster(multiplier, minutes);
                        if(commandSender instanceof org.bukkit.entity.Player) {
                            Player player = (Player) commandSender;
                            player.getInventory().addItem(xpBooster.getItem());
                        }
                        commandSender.sendMessage(ChatColor.GREEN + "You have created an Xp Booster");
                    } else {
                        commandSender.sendMessage(invalid);
                    }
                } catch(NumberFormatException e) {
                    commandSender.sendMessage(invalid);
                }
            } else {
                commandSender.sendMessage(invalid);
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        }
        return true;
    }
}
