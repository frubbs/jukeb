package juke.domain.song.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rafa on 29/11/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GimageSearch {
    private String url;
}
