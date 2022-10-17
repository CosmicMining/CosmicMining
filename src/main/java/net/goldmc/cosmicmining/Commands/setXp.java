package net.goldmc.cosmicmining.Commands;


import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Utilites.Scoreboards;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

import static java.lang.Long.parseLong;
import static org.bukkit.Bukkit.getOfflinePlayer;
import static org.bukkit.Bukkit.getPlayer;

public class setXp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        try {
            long xp = parseLong(args[1]);
            UUID u = UUID.fromString(args[0]);
            YamlDocument levels = Config.getLevels();

            levels.set("Levels." + u.toString() + ".xp", xp);
            Config.setLevels(levels);

            if(sender instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "Xp Saved");
                new Scoreboards(u).prisonsMiningScoreboard();
            } else {
                System.out.println("\u001B[32m" +"Xp Saved" + "\u001B[0m");
                new Scoreboards(u).prisonsMiningScoreboard();
            }
            return true;
        } catch (final NumberFormatException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
