package libert.saehyeon.ssam.util;

import org.bukkit.Location;

public class LocUtil {
    public static String toString(Location loc) {
        StringBuilder sb = new StringBuilder();

        String x = String.format("%.2f", loc.getX());
        String y = String.format("%.2f", loc.getY());
        String z = String.format("%.2f", loc.getZ());

        sb.append(x).append(", ").append(y).append(", ").append(z);

        return sb.toString();
    }
}
