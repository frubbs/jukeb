package com.jukeb.domain.picture;

import juke.domain.picture.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * Created by rafa on 29/12/2015.
 */
@Setter
@Getter
public class PictureListChangedEvent extends ApplicationEvent {
    //private static final long serialVersionUID = -6832091618840903247L;

    private List<Picture> pictureList;

    public PictureListChangedEvent(Object source) {
        super(source);
    }
}
