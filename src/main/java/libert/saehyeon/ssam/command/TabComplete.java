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
                        return TowerManager.towers.stream().map(t -> t.name).collect(Collectors.toList());
                }
            }
        }

        if(s.equals("warp")) {
            return Arrays.asList("open-gui");
        }

        if(s.equals("ssam.api")) {
            return Arrays.asList("점령","시작");
        }
        return null;
    }
}
