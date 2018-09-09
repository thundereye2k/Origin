package win.crune.origin.scoreboard;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public class Entry {

    private String prefix, name, suffix;

    public Entry(String string, int key) {
        this.prefix = "";
        this.name = ChatColor.values()[key].toString();
        this.suffix = "";

        this.name += string.length() > 14 ? string.substring(0, 14) : string;

        if (string.length() >= 16) {
            this.prefix = string.substring(0, 16);
            this.name = ChatColor.values()[key].toString();
            this.name += ChatColor.getLastColors(prefix);
            int len = ChatColor.getLastColors(prefix).length();
            this.name += string.substring(16, string.length() - len);

            if (string.length() > 32) {
                this.prefix = string.substring(0, 16);
                this.name = ChatColor.values()[key].toString();
                this.name += ChatColor.getLastColors(prefix);
                int len2 = ChatColor.getLastColors(prefix).length();

                this.name += string.substring(16, 32 - len2);
                this.suffix = string.substring(32 - len2);

                if (this.suffix.length() > 16) {
                    this.suffix = this.suffix.substring(0, 16);
                }

                return;
            }
        }

        /*
        int keyLength = ChatColor.values()[key].toString().length();
        this.name = ChatColor.values()[key].toString();

        if (string.length() > 16) {
            this.prefix = string.substring(0, 16);
            String lastColors = ChatColor.getLastColors(prefix);
            name += lastColors;

            if (string.length() > 32) {
                name += string.substring(16, 32 - lastColors.length());
                this.suffix = string.substring(32);
            } else {
                name += string.substring(16, string.length() - lastColors.length());
            }

        } else {
            this.name += string.length() > 14 ? string.substring(0, 14) : string;
        }*/

    }
}
