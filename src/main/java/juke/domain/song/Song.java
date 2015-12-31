package juke.domain.song;

import juke.domain.party.Party;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Song {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Party party;

    private String name;

    @ManyToOne
    private Album album;

    private URI location;
    private String lyrics;

    @OneToMany
    private List<Vote> votes;

    private long listPosition;
    //@ManyToOne
   // private PartyUser proposer;

   // @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @CreatedDate
    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    private SongStatus songStatus = SongStatus.QUEUED;
}
