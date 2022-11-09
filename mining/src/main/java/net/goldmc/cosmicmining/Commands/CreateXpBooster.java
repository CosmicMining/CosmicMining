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
        //Check Create Perms
        if(!commandSender.hasPermission("cosmicmining.xpboostercommand")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }

        //Invalid String
        String invalid = ChatColor.RED + "Invalid arguments, use /xpbooster <multiplier> <duration>";


        //Make sure there are 2 args
        if(args.length != 2 && args.length != 3) {
            commandSender.sendMessage(invalid);
            return true;
        }

        //Create the multiplier and duration
        double multiplier;
        int minutes;


        try {
            //Parse the numbers
            multiplier = Double.parseDouble(args[0]);
            minutes = Integer.parseInt(args[1]);
        } catch(NumberFormatException e) {
            //If the numbers are invalid send the invalid string
            commandSender.sendMessage(invalid);
            return true;
        }

        //Check if the multiplier is valid and if the duration is valid
        if(multiplier > 0 && minutes > 0) {
            commandSender.sendMessage(invalid);
            return true;
        }

        //Create the xpbooster
        XpBooster xpBooster = new XpBooster(multiplier, minutes);

        //If the person is a player they get the xpbooster
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.getInventory().addItem(xpBooster.getItem());
            commandSender.sendMessage(ChatColor.GREEN + "You have created an Xp Booster");
            return true;
        }

        //If the commandSender is the console check if there is 3 args <multiplier> <duration> <player>
        if (!(args.length == 3)) {
            commandSender.sendMessage(invalid);
            return true;
        }

        //Get the player and check if the player is invalid
        Player player = commandSender.getServer().getPlayer(args[2]);
        if (player == null || !player.isOnline()) {
            commandSender.sendMessage(ChatColor.RED + "Player not found or is not online");
            return true;
        }

        //Give the player the xpbooster
        player.getInventory().addItem(xpBooster.getItem());
        commandSender.sendMessage(ChatColor.GREEN + "You have created an Xp Booster");

        return true;

    }
}

