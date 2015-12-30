package juke.web.user;

import juke.web.Resource;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class UserResource extends Resource {
    public String name;
    public URI picture;
}
