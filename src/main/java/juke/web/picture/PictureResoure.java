package juke.web.picture;

import juke.domain.user.PartyUser;
import juke.web.Resource;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rafa on 20/12/2015.
 */
@Getter
@Setter
public class PictureResoure extends Resource {
    private PartyUser uploader;
    private String party;
    private Long id;
}
