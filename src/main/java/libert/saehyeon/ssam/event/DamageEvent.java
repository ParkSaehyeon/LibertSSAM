package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.game.GameTimeType;
import libert.saehyeon.ssam.game.GameTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {
    @EventHandler
    void onDamage(EntityDamageEvent e) {
        if(GameTimer.timeType == GameTimeType.OPR_TIME) {

            if(e.getEntity() instanceof Player) {
                e.setCancelled(true);
            }

        }
    }
}
