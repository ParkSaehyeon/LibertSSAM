package libert.saehyeon.ssam;

import org.bukkit.Bukkit;

public class Debug {
    public static void log(String message) {
        if(LibertSSAM.config.DEBUG) {
            Bukkit.broadcastMessage("[디버그] "+message);
        }
    }
}
