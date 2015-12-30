package juke.domain.song;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.net.URI;

@Getter
@Setter
@Entity
public class Artist {
    @Id
    private String name;
    private URI picture;
}
