package juke.domain.picture;

import juke.domain.party.Party;
import juke.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;

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

   // private byte[] bytes;
    private File file;

    @ManyToOne
    private User uploader;

    @ManyToOne
    private Party party;


}
