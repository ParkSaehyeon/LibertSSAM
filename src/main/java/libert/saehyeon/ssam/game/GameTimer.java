package libert.saehyeon.ssam.game;

import libert.saehyeon.ssam.Config;
import libert.saehyeon.ssam.Debug;
import libert.saehyeon.ssam.LibertSSAM;
import libert.saehyeon.ssam.tower.TowerWarp;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class GameTimer {

    private static BossBar bar;

    private static int leftTick;
    private static int maxTick;

    private static BukkitTask task;
    private static BukkitTask oprTimeDelayTask;
    public static GameTimeType timeType;
    public static boolean pause = false;

    /**
     * 탑이 점령되었다면 이 값을 true로 설정하여 전투 시간 동안 탑이 점령되었음을 의미
     */
    public static boolean isAnyTowerDown = false;

    /**
     * Bukkit 스케쥴러 시작
     */
    public static void startTask() {

        cancelTask();

        task = Bukkit.getScheduler().runTaskTimer(LibertSSAM.ins, () -> {

            if(pause) {
                return;
            }

            if(--leftTick > 0) {

                updateBar();

            } else {

                // 전투 시간이 끝남
                if(timeType == GameTimeType.COMBAT_TIME) {
                    onCombatTimeEnd();
                }

                // 작전 시간이 끝남
                else if(timeType == GameTimeType.OPR_TIME) {
                    onOprTimeEnd();
                }
            }


        },0,1);
    }

    /**
     * 1틱 마다 보스바 갱신
     */
    private static void updateBar() {
        if(bar != null) {
            double progress = (double)leftTick / (double)maxTick;

            bar.setProgress(progress);

            if(timeType == GameTimeType.COMBAT_TIME) {
                bar.setTitle(getCombatTimeTitle());
            } else {
                bar.setTitle(getOprTimeTitle());
            }
        }
    }

    /**
     * BukkitTask 취소
     */
    private static void cancelTask() {
        if(task != null) {
            task.cancel();
        }

        task = null;
    }

    /**
     * 전투 시간 종료
     */
    private static void onCombatTimeEnd() {
        Debug.log("전투 시간이 종료됨.");

        if(!isAnyTowerDown) {
            Debug.log("전투 시간 동안 어떠한 타워도 점령되지 않음. 이제부터 각 팀은 서로가 점령한 타워로 워프할 수 있음.");
            TowerWarp.canWarpToTakenTower = true;
        } else {
            Debug.log("전투 시간 동안 1개 이상의 타워가 점령되었으므로 다시 각 팀은 서로 점령한 타워로 워프할 수 없음.");
            TowerWarp.canWarpToTakenTower = false;
        }

        // 전투 시간 동안 탑이 점령되었는 지 여부 초기회
        isAnyTowerDown = false;

        // 작전 시간 시작
        startOprTime();
    }

    /**
     * 작전 시간 종료
     */
    private static void onOprTimeEnd() {
        Debug.log("작전 시간 종료됨.");

        // 전투 시간 시작
        startCombatTime();
    }

    /**
     * 전투 시간의 보스바 타이틀 반환
     */
    private static String getCombatTimeTitle() {

        int leftSecond = leftTick / 20;

        StringBuilder sb = new StringBuilder("지금은 §c§l전투 시간§f입니다!");
        sb.append("§f (").append(leftSecond+1).append(")");

        return sb.toString();
    }

    /**
     * 작전 시간의 보스바 타이틀 반환
     */
    private static String getOprTimeTitle() {

        int leftSecond = leftTick / 20;

        StringBuilder sb = new StringBuilder("지금은 §6§l작전 시간§f입니다!");
        sb.append("§f (").append(leftSecond+1).append(")");

        return sb.toString();
    }

    /**
     * 전투 시간 시작
     */
    public static void startCombatTime() {
        cancelTask();

        String title = getCombatTimeTitle();

        if(bar == null) {
            bar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SOLID);
            Debug.log("보스바가 등록되어 있지 않았으므로 보스바를 자동으로 등록했음.");
        }

        bar.setTitle(title);
        bar.setColor(BarColor.RED);
        bar.setStyle(BarStyle.SOLID);
        bar.setProgress(1);
        showBarAll();

        maxTick    = LibertSSAM.config.COMBAT_TIME_TICK;
        leftTick   = maxTick;
        timeType   = GameTimeType.COMBAT_TIME;

        startTask();

        Debug.log("전투 시간 시작");
    }

    public static void cancelOprTimeDelayTask() {
        if(oprTimeDelayTask != null) {
            oprTimeDelayTask.cancel();
            oprTimeDelayTask = null;
        }
    }

    public static void startOprTimeDelay(int delay) {

        if(delay == 0) {
            startOprTime();
        } else {

            // 타이머 일시중지
            GameTimer.pause = true;

            // 기존의 oprTimeDelayTask 취소
            cancelOprTimeDelayTask();

            oprTimeDelayTask = Bukkit.getScheduler().runTaskLater(LibertSSAM.ins, () -> {

                startOprTime();

                // 타이머 일시중지 해제
                GameTimer.pause = false;

            }, delay);
        }
    }

    /**
     * 작전 시간 시작
     */
    public static void startOprTime() {

        cancelTask();


        String title = getOprTimeTitle();

        BarColor color = BarColor.WHITE;
        BarStyle style = BarStyle.SOLID;

        if(bar == null) {
            bar = Bukkit.createBossBar(title, color, style);
            Debug.log("보스바가 등록되어 있지 않았으므로 보스바를 자동으로 등록했음.");
        }

        bar.setTitle(title);
        bar.setColor(color);
        bar.setStyle(style);
        bar.setProgress(1);
        showBarAll();

        maxTick    = LibertSSAM.config.OPR_TIME_TICK;
        leftTick   = maxTick;
        timeType   = GameTimeType.OPR_TIME;

        startTask();

        // 모든 플레이어를 각 팀의 전초 기지로 보내기
        Bukkit.getOnlinePlayers().forEach(GameTeam::teleportToSpawn);

        Debug.log("작전 시간 시작");
    }

    /**
     * 모든 플레이어에게 보스바를 보여줍니다.
     * 이미 보스바를 출력 중인 플레이어에게는 추가로 출력하지 않습니다.
     */
    public static void showBarAll() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(!bar.getPlayers().contains(p)) {
                bar.addPlayer(p);
            }
        });
    }
    public static void showBar(Player p) {
        if(bar != null) {
            bar.addPlayer(p);
        }
    }

    /**
     * 타이머 중지
     */
    public static void stop() {
        cancelTask();

        leftTick = 0;
        maxTick  = 0;

        if(bar != null) {
            bar.setTitle("§6§l게임이 종료되었습니다!");
            bar.setProgress(0);
        }
    }

    public static void clear() {
        cancelTask();

        // oprTimeDelayTask cancel하기
        if(oprTimeDelayTask != null) {
            oprTimeDelayTask.cancel();
            oprTimeDelayTask = null;
        }

        leftTick = 0;
        maxTick  = 0;
        task = null;

        isAnyTowerDown = false;

        if(bar != null) {
            bar.removeAll();
            bar = null;
        }
    }
}
