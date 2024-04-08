package libert.saehyeon.ssam.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GameItem {
    public static ItemStack createPoint() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta  = item.getItemMeta();

        meta.setDisplayName("§a§l포인트");
        meta.setLore(Arrays.asList("§7사람을 죽이면 얻을 수 있는 전리품입니다."));

        meta.addItemFlags(
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_ATTRIBUTES
        );

        item.setItemMeta(meta);

        return item;
    }

    public static Entity spawnPoint(Location loc) {
        return loc.getWorld().dropItem(loc, createPoint());
    }
}
