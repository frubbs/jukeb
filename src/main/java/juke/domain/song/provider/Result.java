
package juke.domain.song.provider;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Getter
@Setter
public class Result {

    private String GsearchResultClass;
    private String width;
    private String height;
    private String imageId;
    private String tbWidth;
    private String tbHeight;
    private String unescapedUrl;
    private String url;
    private String visibleUrl;
    private String title;
    private String titleNoFormatting;
    private String originalContextUrl;
    private String content;
    private String contentNoFormatting;
    private String tbUrl;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
