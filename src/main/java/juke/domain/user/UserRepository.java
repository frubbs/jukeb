package juke.domain.user;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by rafa on 28/11/2015.
 */
public interface UserRepository   extends CrudRepository<PartyUser, Long> {
}
