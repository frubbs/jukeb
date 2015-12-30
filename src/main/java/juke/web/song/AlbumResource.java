package juke.web.song;

import juke.web.Resource;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class AlbumResource extends Resource {
    private String name;
    private URI picture;
    private ArtistResource artist;
}
