package libert.saehyeon.ssam.game;

import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.tower.Tower;
import libert.saehyeon.ssam.tower.TowerManager;
import org.bukkit.Bukkit;

public class InGameListener {
    public static void onGameStart() {
        Debug.log("[커맨드 → 플러그인] 게임 시작");
        GameTeam.init();
        GameTimer.startCombatTime();
    }

    public static void onTowerTaken(String towerName, String towerTeam, String newTeam) {

        Debug.log("[커맨드 → 플러그인] 탑이 점령됨. §7본래 "+towerTeam+" 팀 소유의 탑인 '"+towerName+"' 탑이 "+newTeam+" 팀에게 점령당함.");

        // 자신들이 점령한 탑을 자신들이 점령하려고 하고 있음.
        if(towerTeam.equals(newTeam)) {
            Bukkit.broadcastMessage("§c본래 "+towerTeam+" 소유의 "+towerName+" 탑을 "+newTeam+" 팀이 점령하도록 요청받았습니다. 같은 팀의 탑을 같은 팀이 점령할 수 없습니다. 커맨드 블럭의 명령어를 수정하세요.");
            return;
        }

        Tower targetTower = TowerManager.findTower(towerName, towerTeam);

        if(targetTower != null) {
            targetTower.setOwner(newTeam);
        } else {
            Bukkit.broadcastMessage("§c점령 오류: 본래 "+towerTeam+" 팀 소유인 탑 '"+towerName+"' 탑을 찾을 수 없습니다. 커맨드 블럭에서 서버에 등록된 탑의 이름으로 점령 정보으로 플러그인에 요청하고 있는 지 확인하십시오.");
            return;
        }

        // 작전 시간 시작
        GameTimer.startOprTime();
    }
}
