package juke.domain.song.repository;

import juke.domain.song.Artist;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rafa on 28/11/2015.
 */
public interface ArtistRepository  extends CrudRepository<Artist, String> {
}
