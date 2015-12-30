package juke.domain.song;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.net.URI;

@Getter
@Setter
@Entity
public class Album {
    @Id
    private String name;
    private URI picture;

    @ManyToOne
    private Artist artist;
}
