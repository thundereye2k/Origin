package win.crune.origin.config;

import java.io.IOException;
import java.util.Map;

/**
 * File config
 */
public interface Config {

    /**
     * Add an object to the config by path
     * @param path   the path
     * @param object the object
     */
    void put(String path, Object object);

    /**
     * Get a map of the config
     * @return map
     */
    Map<String, Object> getMap();

    /**
     * Get an object by its path in the config
     * @param path the path
     * @param <T>  the object
     * @return object
     */
    <T> T get(String path);

    /**
     * Get a string
     * @param path the path
     * @return string
     */
    String getString(String path);

    /**
     * Get an integer
     * @param path the path
     * @return integer
     */
    Integer getInteger(String path);

    /**
     * Get a boolean
     * @param path the path
     * @return boolean
     */
    Boolean getBoolean(String path);

    /**
     * Save the config
     * @throws IOException exception
     */
    void save() throws IOException;

}
