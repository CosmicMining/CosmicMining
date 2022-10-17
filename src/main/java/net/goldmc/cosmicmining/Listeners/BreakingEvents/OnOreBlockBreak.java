package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Leveling.enums;
import net.goldmc.cosmicmining.Utilites.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.*;

public class OnOreBlockBreak implements Listener {
    ArrayList<enums.oreleveling> ores = new ArrayList<>(Arrays.asList(enums.oreleveling.values()));
    ArrayList<enums.blockleveling> blocks = new ArrayList<>(Arrays.asList(enums.blockleveling.values()));

    private boolean isOre(Block b) {
        for (enums.oreleveling ore : ores) {
            //if the block is equal to the ore type
            if (b.getType() == ore.getOre()) {
                return true;
            }

        }
        return false;
    }

    private boolean isOreBlock(Block b) {
        for (enums.blockleveling block : blocks) {
            //if the block is equal to the ore type
            if (b.getType() == block.getBlock()) {
                return true;
            }

        }
        return false;
    }
    @EventHandler
    public void playerOreBlockBreakEvent(BlockBreakEvent e)  {
        Block b = e.getBlock();
        Player p = e.getPlayer();

        if(!p.hasPermission("cosmicmining.minearea.mine")) {
            return;
        }

        //Gets all ore types from enum
        Integer canBreak = PlayerData.canBreak(p, b);


        if(canBreak == null) {
            return;
        }

        BreakingFunctions functions = new BreakingFunctions(canBreak, b, p, false, PlayerData.getBlockType(b));
        if(isOre(b)) {
            functions.setOreBlock(false);
            functions.runMiningBlock();
            e.setCancelled(true);
            return;
        }

        if(isOreBlock(b)) {
            functions.setOreBlock(true);
            functions.runMiningBlock();
            e.setCancelled(true);
        }
    }

    public static HashMap<String, Integer> getOreMap() {
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("COAL", 1);
        hm.put("IRON", 2);
        hm.put("LAPIS", 3);
        hm.put("REDSTONE", 4);
        hm.put("GLOWING", 4);
        hm.put("GOLD", 5);
        hm.put("DIAMOND", 6);
        hm.put("EMERALD", 7);
        return hm;
    }

}
