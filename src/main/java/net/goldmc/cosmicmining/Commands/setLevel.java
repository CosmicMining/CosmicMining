package net.goldmc.cosmicmining.Commands;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.Config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;
import static org.bukkit.Bukkit.getPlayer;

public class setLevel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player || sender instanceof ConsoleCommandSender) {
            if(args.length == 2) {
                if (Bukkit.getOfflinePlayer(UUID.fromString(args[0])) != null) {
                    try {
                        int level = Integer.parseInt(args[1]);
                        Player p = getPlayer(UUID.fromString(args[0]));
                        UUID u = UUID.fromString(args[0]);
                        //Config.getCustomConfig3().set("Levels." + u.toString() + ".level", level);
                        YamlDocument levels = Config.getLevels();
                        levels.set("Levels." + u.toString() + ".level", level);
                        Config.setLevels(levels);
                        if(getOfflinePlayer(u).isOnline()) {
                            p.setLevel(level);
                        }
                        //Config.saveConfig3();
                        if(sender instanceof Player) {
                            ((Player) sender).getPlayer().sendMessage(ChatColor.GREEN + "Level Saved");
                        } else {
                            System.out.println("\u001B[32m" +"Level Saved" + "\u001B[0m");
                        }
                        return true;
                    } catch (final NumberFormatException e) {
                        return false;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
