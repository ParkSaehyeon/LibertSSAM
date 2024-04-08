package libert.saehyeon.ssam;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LibertSSAM extends JavaPlugin {

    public static LibertSSAM ins;

    @Override
    public void onEnable() {
        ins = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void broadcast(String content) {
        Bukkit.broadcastMessage("§6§lSSAM  §f" + content);
    }
}
