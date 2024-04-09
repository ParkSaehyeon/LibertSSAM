package libert.saehyeon.ssam.tower;

import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.game.GameTeam;
import libert.saehyeon.ssam.game.GameTimeType;
import libert.saehyeon.ssam.game.GameTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.Arrays;

public class Tower implements Serializable {

    /** 이 타워의 이름 */
    public String name;

    /** 이 타워의 원래 주인 */
    private String firstOwnTeam;

    /** 이 타워의 현재 주인 */
    private String ownTeam;

    /**
     * 이 타워의 위치
     */
    public Location location;

    public Tower(String name, String ownTeam, Location location) {
        this.name = name;
        this.ownTeam = ownTeam;
        this.firstOwnTeam = this.ownTeam;
        this.location = location;
    }

    /**
     * 이 타워의 주인 팀 변경
     */
    public void setOwner(String teamName) {
        if(firstOwnTeam.equals(teamName)) {
            Bukkit.broadcastMessage("§c본래 "+firstOwnTeam+" 팀이 소유 중이었으며 "+ownTeam+" 팀이 현재 점령 중인 "+name+" 탑을 "+firstOwnTeam+" 팀이 재 탈환합니다. 재 탈환을 방지하는 장치가 마련되어 있는 지 확인하세요. 점령 작업을 취소했습니다.");
            return;
        }

        this.ownTeam = teamName;

        // 전투 시간 중 타워가 점령됨
        if(GameTimer.timeType == GameTimeType.COMBAT_TIME) {
            GameTimer.isAnyTowerDown = true;
        }
    }

    /**
     * 이 타워를 원래 소유하는 팀의 이름을 반환
     */
    public String getOriginOwnTeam() {
        return this.firstOwnTeam;
    }

    /**
     * 이 타워를 현재 소유 중인 팀 이름을 반환
     */
    public String getCurrentOwnTeam() {
        return this.ownTeam;
    }

    /**
     * 타워를 서버에 등록
     */
    public void register() {

        // 이미 같은 타워가 등록되어 있음.
        if(TowerManager.towers.stream().anyMatch(this::equals)) {
            Bukkit.broadcastMessage("§c이미 등록된 타워는 등록할 수 없습니다. 기존 타워를 삭제하거나 정보를 변경하십시오.");
            return;
        }

        TowerManager.towers.add(this);
        Debug.log("타워가 추가됨. (이름: "+name+", 소유 팀: "+ownTeam);
    }

    public boolean equals(Tower tower) {
        return name.equals(tower.name) && ownTeam.equals(tower.ownTeam) && firstOwnTeam.equals(tower.firstOwnTeam);
    }

    public ItemStack toItem() {
        ItemStack item = new ItemStack(LibertSSAM.config.WARP_ITEM_MATERIAL);
        ItemMeta meta  = item.getItemMeta();

        meta.setDisplayName(GameTeam.getTeamColor(getOriginOwnTeam())+"§l"+name.replaceAll("_", " "));
        meta.setLore(Arrays.asList("§7좌클릭§f하여 위치로 이동합니다."));
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);

        return item;
    }

}
