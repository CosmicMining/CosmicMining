package net.goldmc.cosmicmining.BlockBreak.listeners;

import com.cryptomorin.xseries.XMaterial;
import net.goldmc.cosmicmining.BlockBreak.Main;
import net.goldmc.cosmicmining.BlockBreak.utls.Utls;
import net.goldmc.cosmicmining.Utilites.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class BreakListeners implements Listener {

    private static final HashSet<Material> transparentBlocks = new HashSet<>();

    static {
        transparentBlocks.add(XMaterial.WATER.parseMaterial());
        transparentBlocks.add(XMaterial.AIR.parseMaterial());
    }


    @EventHandler
    public void onBlockBreak(BlockDamageEvent event){
        Main.brokenBlocksService.createBrokenBlock(event.getBlock(), 30);
    }

    @EventHandler
    public void onBreakingBlock(PlayerAnimationEvent event){
        Player player = event.getPlayer();

        Block block = player.getTargetBlock(transparentBlocks, 5);
        Object[] canBreak = PlayerData.canBreak(player, block);
        if(Boolean.parseBoolean(canBreak[0].toString())) {
            Location blockPosition = block.getLocation();

            if(!Main.brokenBlocksService.isBrokenBlock(blockPosition))
                Main.brokenBlocksService.createBrokenBlock(block, 30);

        /*
        Use player#getItemInHand for backwards compatibility
         */
            ItemStack itemStack = player.getItemInHand();

            double distanceX = blockPosition.getX() - player.getLocation().getX();
            double distanceY = blockPosition.getY() - player.getLocation().getY();
            double distanceZ = blockPosition.getZ() - player.getLocation().getZ();

            if(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ >= 1024.0D) return;
            Utls.addSlowDig(event.getPlayer(), Integer.MAX_VALUE);
            if(!itemStack.getType().name().replace("PICKAXE", "jhnbefrdjk").contains("jhnbefrdjk")) {
                Main.brokenBlocksService.getBrokenBlock(blockPosition).incrementDamage(player, 0.4);
            }
            else {
                Main.brokenBlocksService.getBrokenBlock(blockPosition).incrementDamage(player, 1);
            }
        } else {
            Main.brokenBlocksService.resetBlock(block.getLocation());
        }
    }
}
