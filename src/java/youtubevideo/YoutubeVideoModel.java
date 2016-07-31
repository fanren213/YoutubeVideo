/*
 * This class is the model of this web application. It gets the search term from
 * servlet and makes HTTP request. It parses the JSON response and generates 
 * JSON replies for the servlet.
 * Author     : Bonan Cao
 */
package youtubevideo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.*;

/**
 *
 * @author Caobonan
 */
public class YoutubeVideoModel {
    private String responseJson;
    private static final String apiKey = "AIzaSyAIXONFefcKTEn0jBP4-nT55YAZggDlLgQ";
    private static final String videoUrlPrefix = "https://www.youtube.com/watch?v=";
    /*
     * This method make HTTP request to youtube and get the JSON response. It 
     * calls a helper method to parse and generate the JSON string.
     */
    public void doYoutubeSearch(String searchTag) {
        String ytbUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + searchTag + "&key=" + apiKey;
        try {
            URL url = new URL(ytbUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            JsonReader rdr = Json.createReader(in);
            JsonObject obj = rdr.readObject();
            responseJson = parseAndGenerateJson(obj);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * This method is a helper method for parsing and generating the JSON string
     * .
     */
    private String parseAndGenerateJson(JsonObject obj) {
        JsonArray results = obj.getJsonArray("items");
        JsonObject result = results.getJsonObject(0);
        String videoId = result.getJsonObject("id").getString("videoId");
        String videoTitle = result.getJsonObject("snippet").getString("title");
        String videoDesc = result.getJsonObject("snippet").getString("description");
        String thumbnailUrl = result.getJsonObject("snippet").getJsonObject("thumbnails").getJsonObject("high").getString("url");
        String publishTime = result.getJsonObject("snippet").getString("publishedAt");
        StringBuffer sb = new StringBuffer();
        sb.append("https://www.youtube.com/watch?v=");
        sb.append(videoId);
        String videoUrl = sb.toString();
        String jsonString = Json.createObjectBuilder()
            .add("videoTitle", videoTitle)
            .add("videoDesc", videoDesc)
            .add("thumbnailUrl", thumbnailUrl)
            .add("publishTime", publishTime)
            .add("videoUrl", videoUrl)
            .build()
            .toString(); 
        
        return jsonString;
    }
    public String getResponseJson() {
        return responseJson;
    }
}
