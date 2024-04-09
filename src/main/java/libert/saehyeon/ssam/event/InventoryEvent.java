package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.tower.Tower;
import libert.saehyeon.ssam.tower.TowerWarp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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

    @EventHandler
    void onInventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals("기지 이동")) {
            if(TowerWarp.noSelectNoClose.contains(e.getPlayer().getUniqueId())) {

                e.getPlayer().sendMessage("§c꼭 선택해야 해요!");

                Bukkit.getScheduler().runTaskLater(LibertSSAM.ins, () -> {
                    TowerWarp.openGUI((Player) e.getPlayer());
                },2);
            }
        }
    }
}
