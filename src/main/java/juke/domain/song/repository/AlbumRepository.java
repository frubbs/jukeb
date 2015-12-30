package juke.domain.song.repository;

import juke.domain.song.Album;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rafa on 28/11/2015.
 */
public interface AlbumRepository  extends CrudRepository<Album, String> {
}
