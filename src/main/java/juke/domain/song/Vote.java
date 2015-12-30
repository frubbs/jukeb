package juke.domain.song;

import juke.domain.user.User;
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
    private User voter;

  /*  @ManyToOne
    private Song song;*/
}
