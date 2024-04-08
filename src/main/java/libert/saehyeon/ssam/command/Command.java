package libert.saehyeon.ssam.command;

import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.game.GameItem;
import libert.saehyeon.ssam.game.GameTimer;
import libert.saehyeon.ssam.tower.Tower;
import libert.saehyeon.ssam.tower.TowerWarp;
import libert.saehyeon.ssam.util.LocUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.util.ArrayList;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        Player p = (Player) commandSender;

        if(s.equals("tower")) {
            switch(strings[0]) {

                /*  타워 설정  */
                case "set":

                    if(strings.length == 3) {
                        Tower t = new Tower(strings[1], strings[2], p.getLocation());
                        t.register();

                        LibertSSAM.broadcast("§7"+strings[2]+" §f팀 소유의 §7"+strings[1]+" 타워를 설정했습니다. 위치는 §7"+LocUtil.toString(p.getLocation())+"§f입니다.");
                    } else {
                        p.sendMessage("§c사용법: /tower set [타워 이름] [초기 소유 팀 이름]");
                    }

                    break;

                /*  타워 삭제  */
                case "remove":

                    if(strings.length == 3) {
                        Tower.removeTower(strings[1], strings[2]);
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

                        Tower targetTower = Tower.getTowerByNameAndTeam(towerName, team);

                        if(targetTower != null) {
                            p.sendMessage("");
                            p.sendMessage("§7"+team+" 팀 소유의 §7"+towerName+"§f 타워의 정보:");
                            p.sendMessage(" → 타워 이름: "+targetTower.name);
                            p.sendMessage(" → 원래 소유 중인 팀: "+targetTower.getOriginOwner());
                            p.sendMessage(" → 현재 소유 중인 팀: "+targetTower.getCurrentOwner());
                        } else {
                            p.sendMessage("§c해당 타워를 찾을 수 없습니다.");
                        }

                    } catch (Exception e) {
                        p.sendMessage("§c사용법: /tower info [타워 이름] [현재 소유 중인 팀 이름]");
                    }
                    break;

                /*  타워 목록  */
                case "list":
                    break;

                /*  현재 타워 상태를 초기 상태로 저장  */
                case "commit":
                    break;
            }
        }

        if(s.equals("ssam.api")) {

            if(strings.length == 0) {
                p.sendMessage("§c올바르지 않은 요청입니다. 플러그인 메뉴얼을 확인하세요.");
                return false;
            }

            switch(strings[0]) {
                case "점령":

                    if(strings.length == 3) {
                        String towerName = strings[1];
                        String team = strings[2];

                        Debug.log("커맨드 블럭에서 탑이 점령됐다고 알려줌. 점령된 타워 이름은 §7"+towerName+"§f, 해당 타워를 점령한 팀 이름은 §7"+team+"§f임.");

                        Tower targetTower = Tower.getTowerByName(team);

                        if(targetTower != null) {
                            targetTower.setOwner(team);
                        }

                        // 작전 시간 시작
                        GameTimer.startOprTime();
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

        return false;
    }
}
