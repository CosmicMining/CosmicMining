package net.goldmc.cosmicmining.Commands;

import net.goldmc.cosmicmining.Utilites.PlayerData;
import net.goldmc.cosmicmining.Utilites.Scoreboards;
import net.goldmc.cosmicmining.dataconnectors.LevelingUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;
import static org.bukkit.Bukkit.getPlayer;

public class setLevel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (PlayerData.commandPlayerChecks(sender, args)) return true;

        try {
            int level = Integer.parseInt(args[1]);
            Player p = getPlayer(UUID.fromString(args[0]));
            UUID u = UUID.fromString(args[0]);
            //Config.getCustomConfig3().set("Levels." + u.toString() + ".level", level);
            LevelingUtils.getInstance().setLevel(u, level);
            if(getOfflinePlayer(u).isOnline()) {
                p.setLevel(level);
            }
            //Config.saveConfig3();
            if(sender instanceof Player) {
                ((Player) sender).getPlayer().sendMessage(ChatColor.GREEN + "Level Saved");
                new Scoreboards(u).prisonsMiningScoreboard();
            } else {
                System.out.println("\u001B[32m" +"Level Saved" + "\u001B[0m");
                new Scoreboards(u).prisonsMiningScoreboard();
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
