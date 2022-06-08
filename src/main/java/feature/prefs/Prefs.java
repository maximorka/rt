package feature.prefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Prefs {
    private Map<String, Object> prefs = new HashMap<>();
    public static final String DEFAULT_PREFS_FILENAME = "prefs.json";
    public static final String DATABASE_CONNECTION_URL ="dbUrl";
    public Prefs() {
        this(DEFAULT_PREFS_FILENAME);
    }

    public Prefs(String fileName) {
        try {
            String json = String.join("\n", Files.readAllLines(Paths.get(fileName)));
            TypeToken<?> typeToken = TypeToken.getParameterized(Map.class, String.class, Object.class);
            prefs = new Gson().fromJson(json,typeToken.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return getPref(key).toString();
    }

    public Object getPref(String key) {
        return prefs.get(key);
    }

    public static void main(String[] args) {

        Prefs prefs = new Prefs();
        System.out.println("prefs = " + prefs.getString("jdbcUrl"));
    }
}
