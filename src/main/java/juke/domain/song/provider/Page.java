
package juke.domain.song.provider;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Getter
@Setter
public class Page {

    private String start;
    private Integer label;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
