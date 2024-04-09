package libert.saehyeon.ssam.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GameItem {
    public static ItemStack createPoint(String victimTeam) {

        // 공격자의 팀
        String attackerTeam = victimTeam.equals("blue") ? "red" : "blue";

        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta  = item.getItemMeta();

        meta.setDisplayName(GameTeam.getTeamColor(attackerTeam)+"§l포인트");
        meta.setLore(Arrays.asList("§f사람을 죽이면 얻을 수 있는 전리품입니다."));

        if(victimTeam.equals("red")) {
            meta.setCustomModelData(1001);
        }

        else if(victimTeam.equals("blue")) {
            meta.setCustomModelData(1002);
        }

        meta.addItemFlags(
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_ATTRIBUTES
        );

        item.setItemMeta(meta);

        return item;
    }

    public static String getVictimTeamFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if(meta != null && meta.getDisplayName() != null && meta.getDisplayName().contains("§l포인트")) {

            boolean victimIsRed  = meta.getCustomModelData() == 1001;
            boolean victimIsBlue = meta.getCustomModelData() == 1002;

            if(victimIsRed) {
                return "red";
            }

            if(victimIsBlue) {
                return "blue";
            }

            Bukkit.broadcastMessage("§c§l포인트 아이템 팀 정보 오류  §c포인트 아이템에서 피해자가 어떤 팀인지 확인할 수 없습니다.");
            return null;
        }

        return null;
    }
}
