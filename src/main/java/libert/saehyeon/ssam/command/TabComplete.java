package libert.saehyeon.ssam.command;

import libert.saehyeon.ssam.tower.Tower;
import libert.saehyeon.ssam.tower.TowerManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equals("tower")) {

            if(strings.length == 1) {
                return Arrays.asList("set","remove","info","list","commit");
            }

            if(strings.length == 2) {
                switch(strings[0]) {
                    case "info":
                        return TowerManager.getTowerNameList();
                }
            }
        }

        if(s.equals("warp")) {
            return Arrays.asList("open-gui");
        }

        if(s.equals("ssam.api")) {

            if(strings.length == 1) {
                return Arrays.asList("점령","시작","준비완료");
            }

            if(strings.length == 2) {
                if(strings[0].equals("점령")) {
                    return TowerManager.getTowerNameList();
                }
            }

            if(strings.length == 3 || strings.length == 4) {
                if(strings[0].equals("점령")) {
                    return Arrays.asList("red","blue");
                }
            }
        }

        if(s.equals("타이머")) {
            return Arrays.asList("전투시간","작전시간");
        }
        return null;
    }
}
