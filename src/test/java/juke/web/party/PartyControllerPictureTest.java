package juke.web.party;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import juke.JukebApplication;
import juke.domain.party.Party;
import juke.domain.party.PartyRepository;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
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

import java.io.File;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by rafa on 20/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JukebApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class PartyControllerPictureTest {

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


    @Test
    public void postPicture() {
        String partyId = rafaParty.getName();

        createParty();

        given().
                multiPart(new File("src/test/resources/chica.jpg")).
                when().
                post("/api/party/{id}/pictures", partyId).
                then().
                statusCode(HttpStatus.SC_CREATED);

    }


    @Ignore
    @Test
    public void listPictures() {
        String partyId = rafaParty.getName();

        createParty();

        postPicture();
        postPicture();
        postPicture();
        postPicture();
        postPicture();


        when().
            get("/api/party/{id}/pictures", partyId).
        then().
            statusCode(HttpStatus.SC_OK).
            body("results", hasSize(5));
    }

    @Test
    @Ignore
    public void getPicture() {
        String partyId = rafaParty.getName();

        createParty();

        postPicture();

        when().
            get("/api/party/{id}/pictures/1", partyId).
        then().
            statusCode(HttpStatus.SC_OK).
           // body("pic.name", Matchers.equalTo(partyId)).
            body("pic", Matchers.equalTo(partyId))
        ;
    }



}
