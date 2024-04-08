package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.game.GameItem;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        Location loc = e.getEntity().getLocation();

        Item item = loc.getWorld().dropItem(loc, GameItem.createPoint());
        item.setGlowing(true);
    }
}
