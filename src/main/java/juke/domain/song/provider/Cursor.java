
package juke.domain.song.provider;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Getter
@Setter
public class Cursor {

    private String resultCount;
    private List<Page> pages = new ArrayList<Page>();
    private String estimatedResultCount;
    private Integer currentPageIndex;
    private String moreResultsUrl;
    private String searchResultTime;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
