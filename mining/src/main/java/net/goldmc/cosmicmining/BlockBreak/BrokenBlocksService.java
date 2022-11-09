package net.goldmc.cosmicmining.BlockBreak;

import net.goldmc.cosmicmining.BlockBreak.objects.BrokenBlock;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;

public class BrokenBlocksService {

    private static final HashMap<Location, BrokenBlock> brokenBlocks = new HashMap<>();

    public void createBrokenBlock(Block block){
        createBrokenBlock(block, -1);
    }

    public void createBrokenBlock(Block block, int time){
        if(isBrokenBlock(block.getLocation())) return;
        BrokenBlock brokenBlock;
        if(time == -1) brokenBlock = new BrokenBlock(block, 0);
        else brokenBlock = new BrokenBlock(block, time);
        brokenBlocks.put(block.getLocation(), brokenBlock);
    }

    public void removeBrokenBLock(Location location){
        brokenBlocks.remove(location);
    }

    public void resetBlock(Location location){
        BrokenBlock brokenBlock = brokenBlocks.get(location);
        if(brokenBlock == null) return;
        brokenBlock.resetDamage();
    }

    public BrokenBlock getBrokenBlock(Location location){
        createBrokenBlock(location.getBlock());
        return brokenBlocks.get(location);
    }

    public boolean isBrokenBlock(Location location){
        return brokenBlocks.containsKey(location);
    }
}
