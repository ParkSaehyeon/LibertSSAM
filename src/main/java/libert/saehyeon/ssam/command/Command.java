package libert.saehyeon.ssam.command;

import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.game.DropItemClear;
import libert.saehyeon.ssam.game.GameTeam;
import libert.saehyeon.ssam.game.GameTimer;
import libert.saehyeon.ssam.game.InGameListener;
import libert.saehyeon.ssam.tower.Tower;
import libert.saehyeon.ssam.tower.TowerManager;
import libert.saehyeon.ssam.tower.TowerWarp;
import libert.saehyeon.ssam.util.LocUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if(s.equals("tower")) {

            Player p = (Player) commandSender;

            switch(strings[0]) {

                /*  타워 설정  */
                case "set":

                    if(strings.length == 3) {
                        Tower t = new Tower(strings[1], strings[2], p.getLocation());
                        t.register();

                        LibertSSAM.broadcast("§7"+strings[2]+" §f팀 소유의 §7"+strings[1]+"§f 타워를 설정했습니다. 위치는 §7"+LocUtil.toString(p.getLocation())+"§f입니다.");
                    } else {
                        p.sendMessage("§c사용법: /tower set [타워 이름] [초기 소유 팀 이름]");
                    }

                    break;

                /*  타워 삭제  */
                case "remove":

                    if(strings.length == 3) {
                        TowerManager.removeTower(strings[1], strings[2]);
                        LibertSSAM.broadcast("§7"+strings[1]+" §f타워를 삭제했습니다.");
                    } else {
                        p.sendMessage("§c사용법: /tower remove [타워 이름] [소유 중인 팀 이름]");
                    }
                    break;

                /*  타워 정보 출력  */
                case "info":
                    try {
                        String towerName = strings[1];
                        String team  = strings[2];

                        Tower targetTower = TowerManager.findTower(towerName, team);

                        if(targetTower != null) {
                            p.sendMessage("");
                            p.sendMessage("§7"+team+"§f 팀 소유의 §7"+towerName+"§f 타워의 정보:");
                            p.sendMessage(" → 타워 이름: "+targetTower.name);
                            p.sendMessage(" → 원래 소유 중인 팀: "+targetTower.getOriginOwnTeam());
                            p.sendMessage(" → 현재 소유 중인 팀: "+targetTower.getCurrentOwnTeam());
                            p.sendMessage(" → 위치: "+LocUtil.toString(targetTower.location));
                            p.sendMessage(" → 점령 여부: "+(targetTower.getOriginOwnTeam().equals(targetTower.getCurrentOwnTeam()) ? "졈령 당하지 않음." : targetTower.getCurrentOwnTeam()+" 팀에게 점령당함."));

                        } else {
                            p.sendMessage("§c해당 타워를 찾을 수 없습니다.");
                        }

                    } catch (Exception e) {
                        p.sendMessage("§c사용법: /tower info [타워 이름] [현재 소유 중인 팀 이름]");
                    }
                    break;

                /*  타워 목록  */
                case "list":
                    p.sendMessage("");
                    p.sendMessage("타워 목록:");
                    for(Tower t : TowerManager.towers) {
                        p.sendMessage("");
                        p.sendMessage(" → "+t.name);
                        p.sendMessage("   → 원래 소유 팀: "+t.getOriginOwnTeam());
                        p.sendMessage("   → 현재 소유 팀: "+t.getCurrentOwnTeam());
                        p.sendMessage("   → 위치: "+LocUtil.toString(t.location));
                        p.sendMessage("   → 점령 여부: "+(t.getOriginOwnTeam().equals(t.getCurrentOwnTeam()) ? "졈령 당하지 않음." : t.getCurrentOwnTeam()+" 팀에게 점령당함."));
                    }
                    break;

                /*  현재 타워 상태를 초기 상태로 저장  */
                case "commit":
                    TowerManager.commit();
                    break;
            }
        }

        if(s.equals("전초기지")) {

            Player p = (Player) commandSender;

            try {
                String teamName = strings[0];

                if(teamName.equals("red")) {
                    LibertSSAM.config.RED_SPAWN = p.getLocation();
                    LibertSSAM.log(teamName+" 팀의 전초 기지 위치가 §7"+LocUtil.toString(p.getLocation())+"§f(으)로 설정되었습니다.");
                }

                else if(teamName.equals("blue")) {
                    LibertSSAM.config.BLUE_SPAWN = p.getLocation();
                    LibertSSAM.log(teamName+" 팀의 전초 기지 위치가 §7"+LocUtil.toString(p.getLocation())+"§f(으)로 설정되었습니다.");
                }

                else {
                    p.sendMessage("§c알 수 없는 팀.");
                }

            } catch (Exception e) {
                p.sendMessage("§c사용법: /spawn [팀 이름]");
            }
        }

        if(s.equals("타이머")) {

            Player p = (Player) commandSender;

            try {

                int sec;

                switch(strings[0]) {
                    case "전투시간":
                        sec = Integer.parseInt(strings[1]);
                        LibertSSAM.config.COMBAT_TIME_TICK = sec * 20;
                        LibertSSAM.broadcast("전투 시간이 §7"+sec+"초("+(sec*20)+" tick)§f으로 설정되었습니다.");
                        break;

                    case "작전시간":
                        sec = Integer.parseInt(strings[1]);
                        LibertSSAM.config.OPR_TIME_TICK = sec * 20;
                        LibertSSAM.broadcast("작전 시간이 §7"+sec+"초("+(sec*20)+" tick)§f으로 설정되었습니다.");
                        break;

                    default:
                        p.sendMessage("§c올바르지 않은 명령입니다. 플러그인 메뉴얼을 참고하세요.");
                        break;
                }
            } catch (Exception e) {
                p.sendMessage("§c올바르지 않은 명령입니다. 플러그인 메뉴얼을 참고하세요.");
            }
        }

        if(s.equals("warp")) {

            Player p = (Player) commandSender;

            switch(strings[0]) {
                case "open-gui":
                    TowerWarp.openGUI(p);
                    break;
            }
        }

        if(s.equals("ssam-debug")) {
            LibertSSAM.config.DEBUG = !LibertSSAM.config.DEBUG;
            LibertSSAM.broadcast("디버그 상태는 이제 다음과 같습니다: "+LibertSSAM.config.DEBUG);
        }

        if(s.equals("ssam.api")) {

            if(strings.length == 0) {
                Bukkit.broadcastMessage("§c올바르지 않은 요청입니다. 플러그인 메뉴얼을 확인하세요.");
                return false;
            }

            switch(strings[0]) {
                case "시작":
                    InGameListener.onGameStart();
                    break;

                case "점령":

                    if(strings.length == 4) {
                        String towerName = strings[1];
                        String towerTeam = strings[2];
                        String newTeam   = strings[3];

                        InGameListener.onTowerTaken(towerName, towerTeam, newTeam);

                    } else {
                        Bukkit.broadcastMessage("§c점령에 대한 요청이 올바르지 않습니다. (사용법: ssam.api 점령 [타워 이름] [타워 초기 소유 팀 이름] [점령한 팀 이름]");
                    }

                    break;

                default:

                    StringBuilder err = new StringBuilder();
                    err.append("§c플러그인에서 커맨드 블럭으로부터 알 수 없는 요청을 수신 받았습니다.");
                    err.append("§c플러그인 메뉴얼을 확인하여 커맨드 블럭 명령어를 수정하세요.");
                    err.append("§c(수신된 명령: ");
                    err.append(s).append(" ").append(String.join(" ", strings)).append(")");

                    Bukkit.broadcastMessage(err.toString());
                    break;
            }
        }

        if(s.equals("종료")) {
            Debug.log("종료 작업 시작:");

            GameTimer.clear();
            Debug.log(" -> 타이머 종료");

            TowerManager.load();
            Debug.log(" -> 타워를 초기 상태로 변경");

            DropItemClear.start();
            Debug.log(" -> 땅에 떨어진 아이템 제거");
        }

        return false;
    }
}
