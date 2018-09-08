package win.crune.origin.config.defaults;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import win.crune.origin.Origin;
import win.crune.origin.config.Config;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class JsonConfig implements Config {

    private static final Gson GSON;

    static {
        GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().
                registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                }).create();
    }

    private String name;
    private File file;
    private Map<String, Object> map;

    public JsonConfig(String name) {
        this.name = name;

        this.file = new File(Origin.getInstance().getDataFolder(), name + ".json");

        if (!file.exists()) {
            file.getParentFile().mkdirs();

            InputStream inputStream = Origin.getInstance().getResource(name + ".json");

            OutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int len;

                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }

                outputStream.close();
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");

            this.map = GSON.fromJson(new JsonReader(reader), HashMap.class);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() throws IOException {
        String jsonString = GSON.toJson(getMap());

        byte[] encodedJson = jsonString.getBytes("UTF8");

        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(encodedJson, 0, encodedJson.length);

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(String path, Object object) {
        if (path.contains(".")) {
            String[] parts = path.split("\\.");

            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) get(parts[0]);
            linkedTreeMap.put(parts[1], object);

            map.put(parts[0], linkedTreeMap);
            return;
        }

        map.put(path, object);
    }

    @Override
    public Map<String, Object> getMap() {
        return ImmutableMap.copyOf(map);
    }

    @Override
    public <T> T get(String path) {
        if (path.contains(".")) {
            String[] parts = path.split("\\.");

            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) map.get(parts[0]);
            return (T) linkedTreeMap.get(parts[1]);
        }

        return (T) map.get(path);
    }

    @Override
    public String getString(String path) {
        return (String) get(path);
    }

    @Override
    public Integer getInteger(String path) {
        return (int) get(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return (boolean) get(path);
    }
}
