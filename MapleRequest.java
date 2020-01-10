import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MapleRequest {

    public static ArrayList<MapleItem> itemRequest(String name, String region, String version) {
        try {
            String request = "https://maplestory.io/api/" + region + "/" + version + "/item?searchFor=" +
                    URLEncoder.encode(name, "UTF-8");
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return parseReponseIntoItems(br);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static BufferedImage iconRequest(String id, String region, String version) {
        try {
            String request = "https://maplestory.io/api/" + region + "/" + version + "/item/" + id + "/icon";
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "");
            return ImageIO.read(conn.getInputStream());
        } catch (IOException e) {
            return null;
        }
    }

    private static ArrayList<MapleItem> parseReponseIntoItems(BufferedReader br) {
        ArrayList<MapleItem> items = new ArrayList<>();
        String name = "";
        String desc = "";
        int id = 0;
        boolean isCash = false;
        String overallCategory = "";
        String category = "";
        String subCategory = "";

        try {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("\"name\": ")) {
                    name = line.substring(line.indexOf("\"name\": ") + 9, line.lastIndexOf("\""));
                } else if(line.contains("\"desc\": ")) {
                    desc = line.substring(line.indexOf("\"desc\": ") + 9, line.lastIndexOf("\""));
                } else if(line.contains("\"id\": ")) {
                    id = Integer.parseInt(line.substring(line.indexOf("\"id\": ") + 6, line.lastIndexOf(",")));
                } else if(line.contains("\"isCash\": ")) {
                    isCash = Boolean.parseBoolean(line.substring(line.indexOf("\"isCash\": ") + 10,
                            line.lastIndexOf(",")));
                } else if(line.contains("\"overallCategory\": ")) {
                    overallCategory = line.substring(line.indexOf("\"overallCategory\": ") + 20,
                            line.lastIndexOf("\""));
                } else if(line.contains("\"category\": ")) {
                    category = line.substring(line.indexOf("\"category\": ") + 13,
                            line.lastIndexOf("\""));
                } else if(line.contains("\"subCategory\": ")) {
                    subCategory = line.substring(line.indexOf("\"subCategory\": ") + 16, line.lastIndexOf("\""));
                    items.add(new MapleItem(name, desc, id, isCash, overallCategory, category, subCategory));
                    name = "";
                    desc = "";
                    id = 0;
                    isCash = false;
                    overallCategory = "";
                    category = "";
                    subCategory = "";
                }
            }
            br.close();
            return items;
        } catch (IOException e) {
            return items;
        }

    }

}
