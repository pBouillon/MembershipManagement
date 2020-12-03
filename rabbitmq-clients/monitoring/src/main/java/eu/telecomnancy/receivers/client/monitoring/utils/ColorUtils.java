package eu.telecomnancy.receivers.client.monitoring.utils;

import java.util.Map;

/**
 * Utility class for color manipulation on the standard output
 */
public class ColorUtils {

    /**
     * Inner-mapping of the {@link Colors} colors and the ANSI color codes
     */
    private static final Map<Colors, String> colorsStringMap = Map.of(
            Colors.CYAN,"\033[0;36m",
            Colors.RESET, "\033[0m",
            Colors.WHITE_BOLD, "\033[1;37m");

    /**
     * This class is meant to be only a tool and should not be instantiated
     */
    private ColorUtils() { }

    /**
     * Convert the provided string to its colored version
     *
     * @param message Message to be colored
     * @param toColor Color to be used for this string
     * @return The colored string
     */
    public static String getColoredString(String message, Colors toColor) {
        return colorsStringMap.get(toColor)
                + message
                + colorsStringMap.get(Colors.RESET);
    }

}
