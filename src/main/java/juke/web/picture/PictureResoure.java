package juke.web.picture;

import juke.domain.user.User;
import juke.web.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

/**
 * Created by rafa on 20/12/2015.
 */
@Getter
@Setter
public class PictureResoure extends Resource {
    private User uploader;
    private String party;
    private Long id;
}
