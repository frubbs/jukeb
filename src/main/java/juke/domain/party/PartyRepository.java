package juke.domain.party;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface PartyRepository extends CrudRepository<Party, String>{

    @Override
    @Transactional
    public Party save(Party p);
}
