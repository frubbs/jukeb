package juke.domain.song;

import juke.domain.user.PartyUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private int vote;

    @ManyToOne
    private PartyUser voter;

  /*  @ManyToOne
    private Song song;*/
}
