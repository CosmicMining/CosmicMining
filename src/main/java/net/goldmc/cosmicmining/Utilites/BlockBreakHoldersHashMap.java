package net.goldmc.cosmicmining.Utilites;

import java.util.HashMap;
import java.util.UUID;

public class BlockBreakHoldersHashMap {
    private final static HashMap<UUID, BlockBreakHolder> blockBreakHolders = new HashMap<>();

    public static void registerBlockHolder(UUID u, BlockBreakHolder b) {
        blockBreakHolders.put(u, b);
    }

    public static BlockBreakHolder getBlockBreakHolder(UUID u) {
        if(blockBreakHolders.containsKey(u)) {
            return blockBreakHolders.get(u);
        }
        return null;
    }
}
