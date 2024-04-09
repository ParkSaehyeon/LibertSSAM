package libert.saehyeon.ssam.tower;

import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.game.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TowerWarp {

    public static boolean isNoSelectNoClose = false;

    /**
     * 점령한 탑으로 이동할 수 있는 지 여부. (false일 경우 점령한 탑으로 이동 불가)
     */
    public static boolean canWarpToTakenTower = false;

    public static void openGUI(Player p) {

        // 팀 이름 설정
        String teamName = GameTeam.getTeam(p);

        if(teamName == null) {
            return;
        }

        // 인벤토리 설정
        Inventory inv = Bukkit.createInventory(null, 27, "기지 이동");

        p.openInventory(inv);

        Debug.log("현재 점령한 다른 팀의 탑으로 워프할 수 있는 가?: "+canWarpToTakenTower);

        int index = 0;

        for(Tower tower : TowerManager.towers) {

            boolean flag = (
                (
                    // 현재 본인 기지인 타워 (원래부터 본인 팀 것이었음)
                    tower.getOriginOwnTeam().equals(tower.getCurrentOwnTeam()) &&
                    tower.getCurrentOwnTeam().equals(teamName)
                ) || (
                    // 현재 본인 팀이 점령한 타워 (원래 본인 팀 것이 아니었음)
                    canWarpToTakenTower &&
                    !tower.getOriginOwnTeam().equals(tower.getCurrentOwnTeam()) &&
                    tower.getCurrentOwnTeam().equals(teamName)
                )
            );

            if(flag) {
                p.getOpenInventory().getTopInventory().setItem(index++, tower.toItem());
            }
        }
    }

    public static void onClickTowerItem(Player player, ItemStack item) {

        if(item.getType() != LibertSSAM.config.WARP_ITEM_MATERIAL) {
            return;
        }

        if(item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) {
            return;
        }

        ItemMeta meta    = item.getItemMeta();
        String itemName  = meta.getDisplayName().replaceAll(" ","_");
        String teamName  = null;
        String towerName = ChatColor.stripColor(itemName);

        if(itemName.contains(GameTeam.getTeamColor("red"))) {
            teamName = "red";
        }

        if(itemName.contains(GameTeam.getTeamColor("blue"))) {
            teamName = "blue";
        }

        if(teamName == null) {
            Bukkit.broadcastMessage("§c"+player.getName()+"(이)가 기지 이동 GUI에서 클릭한 워프 아이템에서 타워의 팀 정보를 읽을 수 없습니다. 워프 아이템으로부터 팀을 읽기 위해 워프 아이템의 이름이 빨간색 혹은 파란색 이여야 합니다.");
            return;
        }

        Tower targetTower = TowerManager.findTowerByOriginTeam(towerName, teamName);

        if(targetTower != null) {
            player.teleport(targetTower.location);
            player.setBedSpawnLocation(targetTower.location, true);
            player.closeInventory();
        } else {
            Bukkit.broadcastMessage("§c"+player.getName()+"(이)가 기지 이동 GUI에서 타워를 클릭하였으나 해당 타워는 존재하지 않습니다. (타워 이름: "+itemName+", 팀 이름: "+teamName+")");
        }

    }

}
