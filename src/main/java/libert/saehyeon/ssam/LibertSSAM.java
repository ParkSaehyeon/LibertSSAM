package libert.saehyeon.ssam;

import libert.saehyeon.ssam.command.Command;
import libert.saehyeon.ssam.command.TabComplete;
import libert.saehyeon.ssam.event.ConnectEvent;
import libert.saehyeon.ssam.event.DeathEvent;
import libert.saehyeon.ssam.event.Event;
import libert.saehyeon.ssam.event.InventoryEvent;
import libert.saehyeon.ssam.game.GameTeam;
import libert.saehyeon.ssam.game.GameTimer;
import libert.saehyeon.ssam.tower.Tower;
import libert.saehyeon.ssam.tower.TowerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class LibertSSAM extends JavaPlugin {

    public static LibertSSAM ins;
    public static Config config;

    @Override
    public void onEnable() {

        ins = this;

        getDataFolder().mkdir();
        Config.load();

        Bukkit.getPluginCommand("tower").setExecutor(new Command());
        Bukkit.getPluginCommand("warp").setExecutor(new Command());
        Bukkit.getPluginCommand("전초기지").setExecutor(new Command());
        Bukkit.getPluginCommand("타이머").setExecutor(new Command());
        Bukkit.getPluginCommand("ssam-debug").setExecutor(new Command());
        Bukkit.getPluginCommand("ssam.api").setExecutor(new Command());

        Bukkit.getPluginCommand("tower").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("warp").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("전초기지").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("타이머").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("ssam-debug").setTabCompleter(new TabComplete());
        Bukkit.getPluginCommand("ssam.api").setTabCompleter(new TabComplete());

        Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Event(), this);
        Bukkit.getPluginManager().registerEvents(new ConnectEvent(), this);

        TowerManager.load();

        Bukkit.getScheduler().runTaskLater(this, () -> {

            GameTeam.init();

        },5);
    }

    @Override
    public void onDisable() {

        GameTimer.clear();
        Config.save();
    }

    public static void log(Level level, String message) {
        ins.getLogger().log(level, message);
    }
    public static void log(String message) {
        log(Level.INFO, message);
    }

    public static void broadcast(String content) {
        Bukkit.broadcastMessage("§6§lSSAM  §f" + content);
    }
}
