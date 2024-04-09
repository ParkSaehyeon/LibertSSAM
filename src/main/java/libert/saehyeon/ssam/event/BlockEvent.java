package libert.saehyeon.ssam.event;

import libert.saehyeon.ssam.game.GameTimeType;
import libert.saehyeon.ssam.game.GameTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockEvent implements Listener {
    @EventHandler
    void onBreak(BlockBreakEvent e) {
        if(GameTimer.isOprWaiting || GameTimer.timeType == GameTimeType.OPR_TIME) {
            e.setCancelled(true);
        }
    }
}
