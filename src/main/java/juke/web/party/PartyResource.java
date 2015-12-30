package juke.web.party;

import juke.web.Resource;
import juke.web.song.SongResource;
import juke.web.user.UserResource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartyResource extends Resource {
    private String name;
    private UserResource owner;
    private List<SongResource> playlist;
}

