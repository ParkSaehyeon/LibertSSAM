package libert.saehyeon.ssam.game;

import libert.saehyeon.ssam.Debug;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DropItemClear {
    public static void start() {
        for(Entity entity : Bukkit.getWorld("world").getEntities()) {
            if(entity.getType() == EntityType.DROPPED_ITEM) {
                entity.remove();
            }
        }

        Debug.log("땅에 떨어진 아이템을 정리했습니다.");
    }
}
