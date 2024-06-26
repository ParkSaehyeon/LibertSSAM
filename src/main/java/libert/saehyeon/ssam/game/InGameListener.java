package libert.saehyeon.ssam.game;

import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.tower.Tower;
import libert.saehyeon.ssam.tower.TowerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class InGameListener {
    public static void onGameStart() {
        Debug.log("[커맨드 → 플러그인] 게임 시작");

        if(LibertSSAM.config.DEBUG) {
            Debug.log("");
            Debug.log("§c§l※ 디버그 모드가 켜져있음! 개발 환경이 아니라면 §f§l/ssam-debug§c§l를 입력하여 해제하시오! ※");
            Debug.log("");
        }

        GameTeam.init();
        //GameTimer.startCombatTime();
        GameTimer.startOprTime(true);
    }

    public static void onTowerTaken(String towerName, String towerTeam, String newTeam) {

        Debug.log("[커맨드 → 플러그인] 탑이 점령됨. §7본래 "+towerTeam+" 팀 소유의 탑인 '"+towerName+"' 탑이 "+newTeam+" 팀에게 점령당함.");

        // 자신들이 점령한 탑을 자신들이 점령하려고 하고 있음.
        if(towerTeam.equals(newTeam)) {
            Bukkit.broadcastMessage("§c본래 "+towerTeam+" 소유의 "+towerName+" 탑을 "+newTeam+" 팀이 점령하도록 요청받았습니다. §c§l§u같은 팀의 탑을 같은 팀이 점령할 수 없습니다. §c커맨드 블럭의 명령어를 수정하세요.");
            return;
        }

        Tower targetTower = TowerManager.findTower(towerName, towerTeam);

        if(targetTower != null) {
            targetTower.setOwner(newTeam);
        } else {
            Bukkit.broadcastMessage("§c본래 "+towerTeam+" 팀 소유인 탑 '"+towerName+"' 탑을 찾을 수 없습니다. §c§l§u커맨드 블럭에서 서버에 등록된 탑의 이름으로 점령 정보으로 플러그인에 요청하고 있는 지 확인하십시오.");
            return;
        }

        // newTeam의 타워 +1
        if(newTeam.equals("red")) {
            TowerManager.RED_TAKEN_TOWER++;
        }

        else if(newTeam.equals("blue")) {
            TowerManager.BLUE_TAKEN_TOWER++;
        }

        // 게임 종료 확인
        String winTeam = "";

        if(TowerManager.RED_TAKEN_TOWER >= 3 && TowerManager.BLUE_TAKEN_TOWER >= 3) {
            winTeam = "draw";
        }

        else if(TowerManager.RED_TAKEN_TOWER >= 3) {
            winTeam = "red";
        }

        else if(TowerManager.BLUE_TAKEN_TOWER >= 3) {
            winTeam = "blue";

        }

        // 이긴 팀 처리
        if(!winTeam.isEmpty()) {
            Debug.log("게임 종료: "+winTeam+" (3개 이상의 탑이 점령됨)");

            String title = "§f§l무승부!";

            if(winTeam.equals("red")) {
                title = "§c§l레드팀 승리!";
            }

            else if(winTeam.equals("blue")) {
                title = "§b§l블루팀 승리!";
            }

            for(Player p2 : Bukkit.getOnlinePlayers()) {

                p2.sendTitle(title, "§f게임이 종료되었어요.");
                p2.playSound(p2.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 0.5f,1);

            }

            // 모두를 스폰으로 TP
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tp @a -1 71 6");

            // 종료 호출
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "종료");
            return;
        }

        // 전투 시간 끝내기
        GameTimer.onCombatTimeEnd(false);

        /*   5초후 작전 시간 시작  */

        // 게임 일시중지
        GameTimer.pause = true;

        // 5초 대기하고 있다고 설정
        GameTimer.isOprWaiting = true;

        // 기존의 oprTimeDelayTask 취소
        GameTimer.cancelOprTimeDelayTask();

        Bukkit.getScheduler().runTaskLater(LibertSSAM.ins, () -> {

            // 작전 시간 시작
            GameTimer.startOprTime(false);

            // 타이머 일시중지 해제
            GameTimer.pause = false;

            // 5초 대기 끝
            GameTimer.isOprWaiting = false;

        },20*5);
    }
}
