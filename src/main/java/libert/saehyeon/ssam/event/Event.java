package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.Config;
import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.tower.TowerWarp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Event implements Listener {
    @EventHandler
    void onStep(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if(!e.getFrom().getBlock().getLocation().equals(e.getTo().getBlock().getLocation())) {

            if(p.getOpenInventory() != null && !p.getOpenInventory().getTitle().equals("기지 이동")) {
                Material floorBlockType = e.getTo().clone().add(0,-1,0).getBlock().getType();

                if(floorBlockType == LibertSSAM.config.WARP_GUI_BLOCK_MATERIAL) {
                    TowerWarp.openGUI(p);
                }
            }
        }
    }
}
