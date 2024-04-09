package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.game.GameItem;
import libert.saehyeon.ssam.game.GameTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ItemEvent implements Listener {
    @EventHandler
    void onItemPickUp(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {

            String playerTeam = GameTeam.getTeam( (Player) e.getEntity() );
            String victimTeam = GameItem.getVictimTeamFromItem( e.getItem().getItemStack() );

            if(playerTeam != null && victimTeam != null) {

                // 같은 팀은 같은 팀이 죽어서 나온 포인트를 먹을 수 없음
                if(playerTeam.equals(victimTeam)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
