import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRow {

    private Map<String, String> map;

    public DataRow(ResultSet rs) throws SQLException {
        List<String> strings = Arrays.asList("model_name", "image_path", "model_number", "manufacturer", "processor", "graphics", "hdd_type", "operating_system");
        List<String> doubles = Arrays.asList("price", "screen_size");
        List<String> ints = Arrays.asList("product_number", "ram_size_gb", "hdd_size_gb");

        map = new HashMap<>();
        for (String str : strings) {
            map.put(str, rs.getString(str));
        }
        for (String str : doubles) {
            map.put(str, ((Double) rs.getDouble(str)).toString());
        }
        for (String str : ints) {
            map.put(str, ((Integer) rs.getInt(str)).toString());
        }

        map.put("model_number", map.get("model_number"));
        map.put("price", "$" + String.format("%.2f", Double.parseDouble(map.get("price"))));

        Integer i = Integer.parseInt(map.get("hdd_size_gb"));
        map.put("hdd",
                ((i < 1000) ? map.get("hdd_size_gb") + "GB" : Integer.valueOf(i / 1000).toString() + "TB" )
                        + " " + map.get("hdd_type"));

        Integer i2 = Integer.parseInt(map.get("ram_size_gb"));
        map.put("ram_size", i2.toString() + "GB");

        map.put("screen_size", map.get("screen_size") + "&quot;");

        map.put("friendly_name",
                map.get("manufacturer") + " " +
                map.get("model_name") + " " +
                map.get("screen_size") + " " +
                "Laptop - " +
                map.get("processor") + " - " +
                map.get("ram_size") + " " +
                map.get("hdd"));
    }

    public String get(String name) {
        return map.get(name);
    }

}
