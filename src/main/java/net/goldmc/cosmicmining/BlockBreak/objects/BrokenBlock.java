package net.goldmc.cosmicmining.BlockBreak.objects;

import net.goldmc.cosmicmining.BlockBreak.PacketStuff;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Date;

/**
 * Copyright © 2019 by Michele Giacalone
 * This file is part of UltraPrison.
 * UltraPrison is under "The 3-Clause BSD License", you can find a copy <a href="https://opensource.org/licenses/BSD-3-Clause">here</a>.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
public class BrokenBlock {

    private static PacketStuff packetStuff = new PacketStuff();
    private int time;
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

    public void destroyBlockObject(){
        damage = 0;
        sendBreakPacket(0);
    }

    public int getAnimation(){
        System.out.println((int) (damage / time *11) - 1);
        return (int) (damage / time *11) - 1;
    }

    public void sendBreakPacket(int animation){
        packetStuff.sendBreakPacket(animation, block);
    }
}
