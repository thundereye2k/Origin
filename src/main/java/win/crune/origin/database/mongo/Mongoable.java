package win.crune.origin.database.mongo;

import org.bson.Document;
import win.crune.origin.store.Storeable;

import java.util.UUID;

/**
 * Easily save and load objects to mongo using documents
 */
public interface Mongoable extends Storeable<UUID> {

    /**
     * Invoked when the object is to be inserted in the database
     * @return document
     */
    Document toDocument();

    /**
     * Invoked when the object is to be loaded from the database
     */
    void fromDocument(Document document);

}
