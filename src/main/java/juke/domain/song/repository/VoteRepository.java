package juke.domain.song.repository;

import juke.domain.song.Vote;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rafa on 28/11/2015.
 */
public interface VoteRepository  extends CrudRepository<Vote, Long> {
}
