package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.tower.TowerWarp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvent implements Listener {
    @EventHandler
    void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(e.getCurrentItem() != null && p.getOpenInventory() != null) {
            if(p.getOpenInventory().getTitle().equals("기지 이동")) {
                e.setCancelled(true);
                TowerWarp.onClickTowerItem(p, e.getCurrentItem());
            }
        }
    }
}
