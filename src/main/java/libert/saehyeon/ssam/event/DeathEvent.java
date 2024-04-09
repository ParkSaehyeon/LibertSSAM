package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.game.GameItem;
import libert.saehyeon.ssam.game.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathEvent implements Listener {
    @EventHandler
    void onDeath(PlayerDeathEvent e) {

        Player p        = e.getEntity();
        Location loc    = e.getEntity().getLocation();
        String teamName = GameTeam.getTeam(e.getEntity());

        if(teamName != null) {
            Item item = loc.getWorld().dropItem(loc, GameItem.createPoint(teamName));
            item.setGlowing(true);
        }

    }

    @EventHandler
    void onRespawn(PlayerRespawnEvent e) {
        Bukkit.getScheduler().runTaskLater(LibertSSAM.ins, () -> {
            e.getPlayer().setHealth(20);
        },2);
    }
}
