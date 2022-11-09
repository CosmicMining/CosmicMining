package net.goldmc.cosmicmining.BlockBreak.objects;

import net.goldmc.cosmicmining.BlockBreak.PacketStuff;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Date;

public class BrokenBlock {

    private static final PacketStuff packetStuff = new PacketStuff();
    private final int time;
    private int oldAnimation;
    private double damage = -1;
    private final Block block;
    private Date lastDamage;

    public BrokenBlock(Block block, int time){
        this.block = block;
        this.time = time;
        lastDamage = new Date();
    }

    public void incrementDamage(Player from, double multiplier){
        if(isBroken()) return;

        damage += multiplier;
        int animation = getAnimation();

        if(animation != oldAnimation){
            if(animation < 10){
                sendBreakPacket(animation);
                lastDamage = new Date();
            } else {
                breakBlock(from);
                return;
            }
        }

        oldAnimation = animation;
    }

    public boolean isBroken(){
        return getAnimation() >= 10;
    }

    public void breakBlock(Player breaker){
        destroyBlockObject();
        if(breaker == null) return;
        packetStuff.sendBreakBlock(breaker, block);
    }

    public void resetDamage(){
        destroyBlockObject();
    }

    public void destroyBlockObject(){
        damage = 0;
        sendBreakPacket(-1);
    }

    public int getAnimation(){
        return (int) (damage / time *11) - 1;
    }

    public void sendBreakPacket(int animation){
        packetStuff.sendBreakPacket(animation, block);
    }
}
