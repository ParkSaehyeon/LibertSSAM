package libert.saehyeon.ssam.game;

import libert.saehyeon.ssam.Config;
import libert.saehyeon.ssam.LibertSSAM;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

public class GameTeam {
    public static String getTeam(Player p) {

        Team redTeam = p.getScoreboard().getTeam("red");
        Team blueTeam = p.getScoreboard().getTeam("blue");

        if(redTeam == null || blueTeam == null) {
            Bukkit.broadcastMessage("§c현재 서버에 팀이 제대로 생성되어 있지 않습니다. 스코어보드 팀에 red, blue 팀이 모두 있는 지 확인하세요.");
            return null;
        }

        boolean isRed = p.getScoreboard().getTeam("red").getEntries().contains(p.getName());
        boolean isBlue = p.getScoreboard().getTeam("blue").getEntries().contains(p.getName());

        if(isRed && isBlue) return "both";
        if(isRed) return "red";
        if(isBlue) return "blue";

        return null;
    }

    public static String getTeamColor(String team) {
        if(team.equals("red")) {
            return "§c";
        }

        if(team.equals("blue")) {
            return "§b";
        }

        return "§f";
    }

    public static void teleportToSpawn(Player player) {
        String teamName = GameTeam.getTeam(player);

        if(teamName == null) {
            return;
        }

        if(teamName.equals("red")) {
            player.teleport(LibertSSAM.config.RED_SPAWN);
        }

        else if(teamName.equals("blue")) {
            player.teleport(LibertSSAM.config.BLUE_SPAWN);
        }
    }
    public static void init() {
        boolean redNull = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("red") == null;
        boolean blueNull = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("blue") == null;

        if(redNull) {
            Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("red");
            LibertSSAM.broadcast("red 팀이 존재하지 않으므로 생성했습니다.");
        }

        if(blueNull) {
            Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("blue");
            LibertSSAM.broadcast("blue 팀이 존재하지 않으므로 생성했습니다.");
        }
    }
}
