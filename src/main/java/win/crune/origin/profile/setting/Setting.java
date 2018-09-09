package win.crune.origin.profile.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import win.crune.origin.store.Storeable;

@Getter
@Setter
@AllArgsConstructor
public class Setting implements Storeable<String> {

    private String value;
    private boolean enabled;
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
