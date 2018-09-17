package win.crune.origin.profile.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import win.crune.origin.store.Storeable;

@AllArgsConstructor
public class Setting implements Storeable<String> {

    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    private boolean enabled;

    @Getter
    @Setter
    private String description;

    @Override
    public String getId() {
        return value;
    }

    @Override
    public void setId(String s) {
        this.value = s;
    }
}
