package juke.domain.song.repository;

import juke.domain.party.Party;
import juke.domain.song.Song;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by rafa on 28/11/2015.
 */
public interface SongRepository extends CrudRepository<Song, Long> {

    //Song findByPartyAndId(Party party, Long Id);
    Song findByPartyAndName(Party party, String name);
   // List<Song> findByParty(Party party, Pageable pageable);
    List<Song> findByPartyOrderByCreationTimeDesc(Party party);


    @Modifying
    @Transactional
    @Query("UPDATE Song s SET s.songStatus = 'PLAYED' WHERE s.party.id = ?1 AND s.songStatus = 'PLAYING' ")
    public void stopAllSongs(String partyId);
}
