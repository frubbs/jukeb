package juke.domain.picture;

import juke.domain.party.Party;
import juke.domain.user.PartyUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by rafa on 20/12/2015.
 */
@Getter
@Setter
@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    //@Lob
    @Column( length = 100000 )
    private byte[] file;
    //private File file;

    @ManyToOne
    private PartyUser uploader;

    @ManyToOne
    private Party party;


}
