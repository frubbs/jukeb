package juke.web.party;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import juke.JukebApplication;
import juke.domain.party.Party;
import juke.domain.party.PartyRepository;
import juke.web.song.AlbumResource;
import juke.web.song.ArtistResource;
import juke.web.song.SongResource;
import juke.web.song.VoteResource;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import  org.hamcrest.*;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static com.jayway.restassured.RestAssured.when;

/**
 * Created by rafa on 30/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JukebApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class PartyControllerTest {
    /*private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
*/

    @Autowired
    private PartyRepository partyRepository;

    @Value("${local.server.port}")
    int port;

    Party rafaParty;

    @Before
    public void setUp() {
        rafaParty = new Party();
        rafaParty.setName("RafaelParty");

        //partyRepository.deleteAll();

        partyRepository.save(rafaParty);


        RestAssured.port = port;
    }
/*
    @Test
    public void voteRakimUp(){
        createParty();
        createSong();
    }
*/
    @Test
    public void listSongs() {
        String partyId = rafaParty.getName();

        createParty();
        createSong(false);

        when().
                get("/api/party/{id}/songs", partyId).
                then().
                statusCode(HttpStatus.SC_OK).
                body("results", hasSize(5))
        ;
    }


    @Test
    public void voteDownSongs() {
        String partyId = rafaParty.getName();

        createParty();
        createSong(false);

        VoteResource vote = new VoteResource();

        vote.setVote(-1);

        given().
                body(vote).
                contentType(ContentType.JSON).
                when().
                post("/api/party/" + partyId + "/songs/1/votes").
                then().
                statusCode(HttpStatus.SC_CREATED);


        when().
                get("/api/party/{id}/songs/1", partyId).
                then().
                statusCode(HttpStatus.SC_OK).
                body("downvotes", Matchers.is(1));

    }

    @Test
    public void voteUpSongs() {
        String partyId = rafaParty.getName();

        createParty();
        createSong(false);

        VoteResource vote = new VoteResource();

        vote.setVote(1);

        given().
                body(vote).
                contentType(ContentType.JSON).
                when().
                post("/api/party/" + partyId + "/songs/1/votes").
                then().
                statusCode(HttpStatus.SC_CREATED);


        when().
                get("/api/party/{id}/songs/1", partyId).
                then().
                statusCode(HttpStatus.SC_OK).
                body("upvotes", Matchers.is(1));

    }



    @Test
    public void getParty() {
        String partyId = rafaParty.getName();

        when().
                get("/api/party/{id}", partyId).
                then().
                statusCode(HttpStatus.SC_OK).
                body("name", Matchers.is(partyId));
    }

    @Test
    public void createParty() {

        PartyResource party = new PartyResource();
        party.setName(rafaParty.getName());

        given().
                body(party).
                contentType(ContentType.JSON).
                when().
                post("/api/party").
                then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", Matchers.is(rafaParty.getName()));
    }

    @Ignore
    @Test
    public void createSong() {
        createSong(true);
    }


        public void createSong(boolean verifySongs) {

        postSong(songFactory(0),verifySongs);
        postSong(songFactory(1),verifySongs);
        postSong(songFactory(2),verifySongs);
        postSong(songFactory(3),verifySongs);
        postSong(songFactory(4),verifySongs);
    }

    private SongResource songFactory(int i) {
        String[] artists = {"Eric B. and Rakim", "Method Man", "Dr Dre", "Snoop Doggy dogg", "Hibria"};
        String[] albuns = {"Paid in full", "The Math lab", "2001", "DoggyStyle", "The skull collectors"};
        String[] songs = {"My melody", "Bang zoom", "the next episode", "lodi dodi", "Tiger punch"};

        SongResource song = new SongResource();
        song.setName(songs[i]);
        AlbumResource a = new AlbumResource();
        a.setName(albuns[i]);

        ArtistResource ar = new ArtistResource();
        ar.setName(artists[i]);

        a.setArtist(ar);

        song.setAlbum(a);
        return song;
    }




    private void postSong(SongResource song, boolean verifySong) {
        if (verifySong)
            given().
                    body(song).
                    contentType(ContentType.JSON).
                    when().
                    post("/api/party/" + rafaParty.getName() +"/songs").
                    then().
                    statusCode(HttpStatus.SC_CREATED).
                    body("name", Matchers.is(song.getName())).
                    body("album.name",Matchers.is(song.getAlbum().getName())).
                    body("album.artist.name",Matchers.is(song.getAlbum().getArtist().getName())).
                    body("album.picture",Matchers.notNullValue()).
                    body("album.artist.picture",Matchers.notNullValue()).
                    body("upvotes",Matchers.is(0)).
                    body("downvotes",Matchers.is(0));
        else
            given().
                    body(song).
                    contentType(ContentType.JSON).
                    when().
                    post("/api/party/" + rafaParty.getName() +"/songs").
                    then().
                    statusCode(HttpStatus.SC_CREATED);
    }








}
