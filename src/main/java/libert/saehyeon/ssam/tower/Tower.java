package libert.saehyeon.ssam.tower;

import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.game.GameTimeType;
import libert.saehyeon.ssam.game.GameTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tower {

    public static List<Tower> towers = new ArrayList<>();

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
            Debug.log(teamName+" 팀이 "+ownTeam+" 팀으로부터 다시 "+name+" 탑을 되찾음.");
        } else {
            Debug.log(teamName+" 팀이 "+ownTeam+" 팀 소유의 "+name+" 탑을 점령함.");
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
    public String getOriginOwner() {
        return this.firstOwnTeam;
    }

    /**
     * 이 타워를 현재 소유 중인 팀 이름을 반환
     */
    public String getCurrentOwner() {
        return this.ownTeam;
    }

    /**
     * 타워를 서버에 등록
     */
    public void register() {

        // 이미 같은 타워가 등록되어 있음.
        if(towers.stream().anyMatch(this::equals)) {
            Bukkit.broadcastMessage("§c이미 등록된 타워는 등록할 수 없습니다. 기존 타워를 삭제하거나 정보를 변경하십시오.");
            return;
        }

        towers.add(this);
        Debug.log("타워가 추가됨. (이름: "+name+", 소유 팀: "+ownTeam);
    }

    public boolean equals(Tower tower) {
        return name.equals(tower.name) && ownTeam.equals(tower.ownTeam) && firstOwnTeam.equals(tower.firstOwnTeam);
    }

    public static void removeTower(String towerName, String ownTeam) {
        towers = towers.stream().filter(t -> !t.name.equals(towerName) && !t.ownTeam.equals(ownTeam)).collect(Collectors.toList());
    }

    public static Tower getTowerByName(String towerName) {

        for(Tower t : towers) {
            if(t.name.equals(towerName)) {
                return t;
            }
        }

        return null;
    }

    public static Tower getTowerByNameAndTeam(String towerName, String team) {
        for(Tower t : towers) {
            if(t.name.equals(towerName) && t.ownTeam.equals(team)) {
                return t;
            }
        }

        return null;
    }
}
