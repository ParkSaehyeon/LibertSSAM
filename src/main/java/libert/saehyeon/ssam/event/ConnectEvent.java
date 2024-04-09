package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.game.GameTimer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectEvent implements Listener {
    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(LibertSSAM.ins, () -> {
            GameTimer.showBar(e.getPlayer());
        },2);

    }
}
