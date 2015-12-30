package juke.web.song;

import juke.web.Resource;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class ArtistResource extends Resource {
    private String name;
    private URI picture;
}
