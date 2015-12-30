package juke.domain.picture;

import juke.domain.party.Party;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rafa on 20/12/2015.
 */
public interface PictureRepository extends CrudRepository<Picture, Long> {
    List<Picture> findByParty(Party party);
    Picture findByIdAndParty(Long id, Party party);

}
