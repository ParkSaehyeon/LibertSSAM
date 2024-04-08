package libert.saehyeon.ssam.command;

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
        if(s.equals("ssam")) {
            switch(strings.length) {
                case 1:
                    return Arrays.asList(
                            "get-point",
                            "spawn-point",
                            "open-warp-gui",
                            "add-loc",
                            "rm-loc",
                            "ls-item",
                            "ls-warp",
                            "add-warp",
                            "rm-warp",
                            "warp-info"
                    );

                case 3:

                    if(strings[1].equals("add-loc")) {
                        return Arrays.stream(Material.values()).map(Enum::toString).collect(Collectors.toList());
                    }
                    break;
            }
        }
        return null;
    }
}
