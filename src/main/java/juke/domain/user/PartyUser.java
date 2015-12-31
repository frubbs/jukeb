package juke.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URI;

/**
 * Created by rafa on 28/11/2015.
 */
@Getter
@Setter
@Entity
public class PartyUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    public String name;
    public URI picture;
}
