package net.goldmc.cosmicmining.Leveling;

import org.bukkit.Material;

public class enums {
    enum armorleveling {
        LEATHER,
        CHAINMAIL,
        GOLD,
        IRON,
        DIAMOND
    }
    enum pickaxeleveling {
        WOODEN(Material.WOOD_PICKAXE),
        STONE(Material.STONE_PICKAXE),
        GOLDEN(Material.GOLD_PICKAXE),
        IRON(Material.IRON_PICKAXE),
        DIAMOND(Material.DIAMOND_PICKAXE);


        private final Material material;
        pickaxeleveling(Material pickaxe) {
            this.material = pickaxe;
        }
        public Material getMaterial() {
            return material;
        }
    }
    enum swordleveling {
        WOOD(Material.WOOD_SWORD),
        STONE(Material.STONE_SWORD),
        GOLD(Material.GOLD_SWORD),
        IRON(Material.IRON_SWORD),
        DIAMOND(Material.DIAMOND_SWORD);
        private Material sword;
        swordleveling(Material sword) {
            this.sword = sword;
        }

        public Material getSword() {
            return sword;
        }
    }
    public enum oreleveling {
        COAL(Material.COAL_ORE),
        IRON(Material.IRON_ORE),
        LAPIS(Material.LAPIS_ORE),
        REDSTONE(Material.REDSTONE_ORE),
        GLOWING(Material.GLOWING_REDSTONE_ORE),
        GOLD(Material.GOLD_ORE),
        DIAMOND(Material.DIAMOND_ORE),
        EMERALD(Material.EMERALD_ORE);
        private final Material ore;

        oreleveling(Material ore) {
            this.ore = ore;
        }
        public Material getOre() {
            return ore;
        }
    }
    public enum blockleveling {
        COAL(Material.COAL_BLOCK),
        IRON(Material.IRON_BLOCK),
        LAPIS(Material.LAPIS_BLOCK),
        REDSTONE(Material.REDSTONE_BLOCK),
        GOLD(Material.GOLD_BLOCK),
        DIAMOND(Material.DIAMOND_BLOCK),
        EMERALD(Material.EMERALD_BLOCK);
        private final Material block;
        blockleveling(Material block) {
            this.block = block;
        }

        public Material getBlock() {
            return block;
        }
    }
}
