package juke.domain.party;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by rafa on 22/11/2015.
 */
@Getter
@Setter
@Entity
public class Party{
    @Id
    private String name;

   /* @OneToOne
    private PartyUser owner;*/
}
