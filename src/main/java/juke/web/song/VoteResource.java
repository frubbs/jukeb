package juke.web.song;

import juke.web.Resource;
import juke.web.user.UserResource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteResource extends Resource {
    private int vote;
    private UserResource voter;
}
