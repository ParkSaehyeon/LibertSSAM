package libert.saehyeon.ssam;

import libert.saehyeon.ssam.util.FileUtil;
import libert.saehyeon.ssam.util.Serialize;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class Config implements Serializable {
    public boolean DEBUG = false;

    public Location RED_SPAWN = null;
    public Location BLUE_SPAWN = null;
    public int COMBAT_TIME_TICK = 20*20; //= 10*60*20; // 기본 = 10분
    public int OPR_TIME_TICK = 10*20; //= 6*60*20; // 기본 = 6분

    public String TOWER_CONFIG = LibertSSAM.ins.getDataFolder()+"/tower.sae";

    public Material WARP_GUI_BLOCK_MATERIAL = Material.EMERALD_BLOCK;
    public Material WARP_ITEM_MATERIAL = Material.SHULKER_SHELL;

    public Config() { }

    public static void load() {
        try {

            String path = LibertSSAM.ins.getDataFolder()+"/config.sae";

            if(FileUtil.exists(path)) {
                String base64 = FileUtil.readFile(path);

                LibertSSAM.config = (Config) Serialize.deSerialize(base64);
                LibertSSAM.log("설정을 불러왔습니다.");
            } else {
                LibertSSAM.config = new Config();
                LibertSSAM.log("설정을 새로 생성했습니다.");
                new File(path).createNewFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {

            if(LibertSSAM.config == null) {
                LibertSSAM.config = new Config();
            }

            String path = LibertSSAM.ins.getDataFolder()+"/config.sae";
            String base64 = Serialize.serialize(LibertSSAM.config);

            FileUtil.writeFile(path, base64);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
