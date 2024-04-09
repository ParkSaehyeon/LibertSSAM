package libert.saehyeon.ssam.tower;

import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.util.FileUtil;
import libert.saehyeon.ssam.util.Serialize;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TowerManager {
    public static List<Tower> towers = new ArrayList<>();

    public static void removeTower(String towerName, String ownTeam) {
        towers = towers.stream().filter(t -> !t.name.equals(towerName) && !t.getCurrentOwnTeam().equals(ownTeam)).collect(Collectors.toList());
    }

    public static Tower findTower(String towerName, String team) {
        return findTower(towerName, team, false);
    }

    /**
     * 등록된 타워 중 특정 이름, 특정 팀에 소속되어 있거나 본래 소유의 팀일 경우의 타워를 반환.
     * @param towerName 검색할 타워 이름
     * @param team 소유 중인 팀 또는 본래 소유 팀
     * @param originTeam true일 경우, team 인자를 현재 점령 중인 팀이 아닌 본래 소유 중이었던 팀으로 검색합니다. false일 경우, team 인자를 해당 팀이 점령한 타워로 검색합니다.
     * @return
     */
    public static Tower findTower(String towerName, String team, boolean originTeam) {

        towerName = towerName.replaceAll(" ", "_");

        for(Tower t : towers) {

            if(!originTeam) {
                if(t.name.equals(towerName) && t.getCurrentOwnTeam().equals(team)) {
                    return t;
                }
            } else {
                if(t.name.equals(towerName) && t.getOriginOwnTeam().equals(team)) {
                    return t;
                }
            }
        }

        return null;
    }

    public static Tower findTowerByOriginTeam(String towerName, String team) {
        return findTower(towerName, team, true);
    }

    public static void commit() {
        String str = Serialize.serialize(towers);
        FileUtil.writeFile(LibertSSAM.config.TOWER_CONFIG, str);

        LibertSSAM.broadcast(towers.size()+"개의 타워를 커밋했습니다.");
    }

    public static void load() {

        try {

            Object towersObj = Serialize.deSerializeFile(LibertSSAM.config.TOWER_CONFIG);

            if(towersObj != null) {
                towers = (List<Tower>) towersObj;
                LibertSSAM.log("타워 설정을 로드했습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.broadcastMessage("§cTower 클래스 구조가 변경되었기 때문에 현재 저장된 탑 정보를 가져오지 못했습니다. 타워 설정을 다시해야합니다.");
        }
    }

}
