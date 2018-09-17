package win.crune.origin.rank;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import win.crune.origin.Origin;
import win.crune.origin.database.mongo.Mongoable;
import win.crune.origin.store.Storeable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Rank implements Mongoable {

    private UUID uuid;
    private String name, prefix, suffix;
    private Set<String> permissions;
    private Rank inherited;
    private boolean global;

    public Rank(UUID uuid) {
        this.uuid = uuid;
        this.name = "";
        this.prefix = "";
        this.suffix = "";
        this.permissions = Sets.newHashSet();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Document toDocument() {
        Document toReturn = new Document();

        toReturn.append("name", name);
        toReturn.append("prefix", prefix);
        toReturn.append("suffix", suffix);
        toReturn.append("global", global);
        toReturn.append("inherited", inherited == null ? "??????" : inherited.getId().toString());
        toReturn.append("permissions", new ArrayList<>(permissions).toString());

        return toReturn;
    }

    @Override
    public void fromDocument(Document document) {
        RankHandler rankHandler = (RankHandler) Origin.getInstance().getHandlerStore().get("rank");
        this.name = document.getString("name");
        this.prefix = document.getString("prefix");
        this.suffix = document.getString("suffix");
        this.global = document.getBoolean("global");

        try {
            this.inherited = rankHandler.getRankStore().get(UUID.fromString(document.getString("inherited")));
        } catch (IllegalArgumentException e) {
            //ignore
        }

        String permissions = document.getString("permissions");
        String[] array = permissions.replace("[", "").replace("]", "").split(",");
        this.permissions.addAll(Arrays.asList(array));
    }
}
