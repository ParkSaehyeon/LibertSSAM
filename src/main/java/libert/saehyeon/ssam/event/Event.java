package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.warp.WarpItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Event implements Listener {
    @EventHandler
    void onStep(PlayerMoveEvent e) {
        if(!e.getFrom().getBlock().getLocation().equals(e.getTo().getBlock().getLocation())) {

        }
    }

    @EventHandler
    void onItemClick(InventoryClickEvent e) {
        if(e.getCurrentItem() != null) {
            WarpItem.onClick((Player) e.getWhoClicked(),e.getCurrentItem());
        }
    }
}
