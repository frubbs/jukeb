package juke.domain.song.provider;

import juke.domain.song.Artist;
import juke.domain.song.repository.AlbumRepository;
import juke.domain.song.repository.ArtistRepository;
import juke.domain.song.Album;
import juke.domain.song.Song;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
/*
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
*/


@Component
public class SongProvider {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private AlbumRepository albumRepository;


    public String getLyrics(Song song){
        return "Letra(" + song + ")";
    }

    public URI getArtistPicture(Song song){
        if(song.getAlbum() != null) {
            if (song.getAlbum().getArtist() != null) {
                Artist artist = artistRepository.findOne(song.getAlbum().getArtist().getName());
                if (artist != null && artist.getPicture() != null)
                    return artist.getPicture();
                if (!song.getAlbum().getArtist().getName().equals(""))
                    return getImageURLFromGoogle(song.getAlbum().getArtist().getName());
            }
        }
        return null;
    }

    public URI getAlbumPicture(Song song){
        if(song.getAlbum() != null){

            Album album = albumRepository.findOne(song.getAlbum().getName());
            if (album != null) {
                if (album.getPicture() != null) {
                    return album.getPicture();
                }
                if (!album.getName().equals("")) {
                    return getImageURLFromGoogle(album.getArtist().getName() + " " + album.getName() + " album");
                }
            }

            if (!song.getAlbum().getName().equals("")) {
                if (song.getAlbum().getArtist() != null && !song.getAlbum().getArtist().getName().equals("")) {
                    return getImageURLFromGoogle(song.getAlbum().getName() + " album " + song.getAlbum().getArtist().getName());
                }
                return getImageURLFromGoogle(song.getAlbum().getName());
            }
        }
        return null;
    }

    private static URI getImageURLFromGoogle(String name) {

        if (true)
            return null;

        //TODO integrar com essa merda!

        URL url = null;
        String baseUrl = "https://www.google.com/search?tbm=isch&q=@PARAM@";

        String finUrl = "";

        try {
            String googleUrl = baseUrl.replace("@PARAM@", URLEncoder.encode(name, "UTF-8"));
            System.out.println("url: " + googleUrl);
            Document doc1 = Jsoup.connect(googleUrl).userAgent("Mozilla/5.0").timeout(10 * 1000).get();
            Element media = doc1.select("[data-src]").first();
            finUrl = media.attr("abs:data-src");

           // finRes= "<a href=\"http://images.google.com/search?tbm=isch&q=" + question + "\"><img src=\"" + finUrl.replace("&quot", "") + "\" border=1/></a>";
            System.out.println(finUrl);
            return new URI(finUrl);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("########## ERRO: " + e.getMessage());
            //http://stackoverflow.com/questions/34035422/google-image-search-says-api-no-longer-available
        }

        return null;

    }



    private static URI getImageURLFromGoogleLegacy(String name) {
        URL url = null;
        String baseUrl = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=@PARAM@";


        try {
            url = new URL(baseUrl.replace("@PARAM@", URLEncoder.encode(name, "UTF-8")));
            System.out.println("Url: " + url);
            InputStream is = url.openStream();
            JsonReader rdr = Json.createReader(is);

            JsonObject obj = rdr.readObject();
            //JsonArray results = obj.getJsonArray("responseData");
            JsonObject responseData = obj.getJsonObject("responseData");
            JsonArray results = responseData.getJsonArray("results");

            if(results != null &&  results.size() > 0) {
                return new URI(results.getValuesAs(JsonObject.class).get(0).getString("url"));
            }

                /*for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    //System.out.print(result.getJsonObject("url").getString("name"));
                    System.out.print(": ");
                    System.out.println(result.getString("url"));
                    System.out.println("-----------");
                }*/

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("########## ERRO: " + e.getMessage());
            //http://stackoverflow.com/questions/34035422/google-image-search-says-api-no-longer-available
        }
        return null;
    }

}
