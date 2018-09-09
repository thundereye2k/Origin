package win.crune.origin.chat;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Chat coloring
 */
public class Chat {

    /**
     * The color character to be translated in the methods below.
     */
    private static final char COLOR_CHAR = '&';

    /**
     * Translate a string with chatcolors
     * @param string the string
     * @return colorized string
     */
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, string);
    }

    /**
     * Translate a string[] with chatcolors
     * @param strings the strings
     * @return colorized string[]
     */
    public static String[] color(String... strings) {
        return color(Arrays.asList(strings)).toArray(new String[strings.length]);
    }

    /**
     * Translate a collection of strings with chatcolors
     * @param t the collection
     * @return colorized list<String>
     */
    public static List<String> color(Collection<String> t) {
        return t.stream().map(Chat::color).collect(Collectors.toList());
    }

}
