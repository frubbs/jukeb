package juke;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by rafa on 29/11/2015.
 */
public class teste {
    public static void main(String[] args) {
        System.out.println("bananaaa");

        String name = "jorge ben jor";
        URL urlResult = null;


        urlResult = getImageURLFromGoogle(name);

        System.out.println("url: " + urlResult.toString());

    }

    private static URL getImageURLFromGoogle(String name) {
        URL url = null;
        String baseUrl = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=@PARAM@";


        try {
            url = new URL(baseUrl.replace("@PARAM@", URLEncoder.encode(name, "UTF-8")));
            try (InputStream is = url.openStream();
                 JsonReader rdr = Json.createReader(is)) {

                JsonObject obj = rdr.readObject();
                //JsonArray results = obj.getJsonArray("responseData");
                JsonObject responseData = obj.getJsonObject("responseData");
                JsonArray results = responseData.getJsonArray("results");

                if(results != null &&  results.size() > 0) {
                    return new URL(results.getValuesAs(JsonObject.class).get(0).getString("url"));
                }

                /*for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    //System.out.print(result.getJsonObject("url").getString("name"));
                    System.out.print(": ");
                    System.out.println(result.getString("url"));
                    System.out.println("-----------");
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
