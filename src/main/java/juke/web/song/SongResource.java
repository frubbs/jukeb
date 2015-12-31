package juke.web.song;

import juke.domain.song.SongStatus;
import juke.web.Resource;
import juke.web.user.UserResource;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@Setter
public class SongResource extends Resource {
    private long id;
    private String name;

    private AlbumResource album;
    private URI location;
    private String lyrics;
    private int upvotes;
    private int downvotes;
    private UserResource proposer;

    private long listPosition;
    private SongStatus songStatus;


}
