package juke.domain.party;

import com.jukeb.domain.picture.PictureListChangedEvent;
import juke.domain.picture.Picture;
import juke.domain.picture.PictureRepository;
import juke.domain.song.provider.SongProvider;
import juke.domain.song.repository.AlbumRepository;
import juke.domain.song.repository.ArtistRepository;
import juke.domain.song.repository.SongRepository;
import juke.domain.song.repository.VoteRepository;
import juke.domain.user.User;
import juke.domain.user.UserRepository;
import juke.exception.NotFoundException;
import juke.domain.song.Song;
import juke.domain.song.SongStatus;
import juke.domain.song.Vote;
import lombok.Getter;
import lombok.Setter;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Getter
@Setter
public class PartyService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongProvider songProvider;

    @Autowired
    private MapperFacade mapperFacade;

    private @Autowired
    ApplicationEventPublisher publisher;

    public Party create(Party p) {
        Party party = new Party();
        party.setName(p.getName());

        partyRepository.save(party);

        return party;
    }

    public Song addSong(String partyName, Song song) {

        Party party = partyRepository.findOne(partyName);
        song.setParty(party);



        song.getAlbum().getArtist().setPicture(songProvider.getArtistPicture(song));

        artistRepository.save(song.getAlbum().getArtist());

        song.getAlbum().setPicture(songProvider.getAlbumPicture(song));

        albumRepository.save(song.getAlbum());


        song.setCreationTime(LocalDateTime.now());
        songRepository.save(song);

        return song;
    }

    public void voteSong(String partyName, Long songId, Vote vote) {

        User user = null;
        if (vote.getVoter() != null && vote.getVoter().getId() != 0)
             user = userRepository.findOne(vote.getVoter().getId());

        if (user == null && vote.getVoter() != null)
            userRepository.save(vote.getVoter());

        Party party = partyRepository.findOne(partyName);
        Song song = getSong(partyName, songId);
        song.getVotes().add(vote);



        voteRepository.save(vote);
    }

    public Party getParty(String partyName) throws NotFoundException {

        Party party = partyRepository.findOne(partyName);

        if (party == null)
            throw new NotFoundException();

        return party;



    }

    public Song getSong(String partyName, Long songId) {

        Party party = partyRepository.findOne(partyName);

        Song song = null; // songRepository.findByPartyAndId(party, songId); //comentado pq mudou para findByPartyAndName

        if (song == null)
            throw new NotFoundException();

        return song;
    }

    public List<Song> getSongs(String partyName) {
        Party party = partyRepository.findOne(partyName);

        List<Song> songs = songRepository.findByPartyOrderByCreationTimeDesc(party);
        if (songs == null || songs.size() <= 0)
            return new ArrayList<Song>();


        Stream<Song> orderedSongs = songs.stream()
                .sorted(
                    (o1, o2) -> Long.compare(
                                o2.getVotes().stream().collect(Collectors.summingInt(Vote::getVote)),
                                o1.getVotes().stream().collect(Collectors.summingInt(Vote::getVote))
                            )
                )
                .sorted(
                    (o1, o2) -> Boolean.compare(
                            (o2.getSongStatus().equals(SongStatus.PLAYING)),
                            (o1.getSongStatus().equals(SongStatus.PLAYING))
                    )
                );
        return orderedSongs.collect(Collectors.toList());
    }

    public List<Vote> getVotes(String partyName, Long songId) {
        Party party = partyRepository.findOne(partyName);
        //Comentado por que mudou para findByPartyAndName
        //Song song = songRepository.findByPartyAndId(party,songId);
       // return  song.getVotes();
        return null;
    }

    public void updatePartySongs(String partyName, List<Song> songs) {
        Party party = partyRepository.findOne(partyName);

        if(party == null)
            throw new NotFoundException();

        for(Song s : songs) {
            //Song song = songRepository.findByPartyAndId(party, s.getId());
            //System.out.println("s.getSongStatus():" + s.getSongStatus());
            //garantir que apenas uma esta tocando
            if (s.getSongStatus() == SongStatus.PLAYING) {
                songRepository.stopAllSongs(party.getName());
            }

            Song song = songRepository.findByPartyAndName(party, s.getName());

            if (song == null) {
                song = new Song();
                mapperFacade.map(s, song);//sobrescreve os valores preenchidos. os null sao ignorados (JukebConfigurableMapper)
                addSong(partyName, song);
            }
            else {
                mapperFacade.map(s, song);//sobrescreve os valores preenchidos. os null sao ignorados (JukebConfigurableMapper)
                try{
                    songRepository.save(song);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void savePicture(String partyName, byte[] bytes) {

        Picture pic = new Picture();
/*
        String fileName = "src/test/resources/" + java.util.UUID.randomUUID();

        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            stream.write(bytes);
            stream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
*/
       // pic.setFile(new File(fileName));

        pic.setFile(DatatypeConverter.parseBase64Binary(DatatypeConverter.printBase64Binary(bytes)));
        Party p = partyRepository.findOne(partyName);
        pic.setParty(p);

        pictureRepository.save(pic);

        PictureListChangedEvent event = new PictureListChangedEvent(this);
        event.setPictureList(getPictures(partyName));
        publisher.publishEvent(event);

    }

    public List<Picture> getPictures(String partyName) {
        return pictureRepository.findByParty(partyRepository.findOne(partyName));
    }

    public Picture getPicture(String partyName, Long id) {
        return pictureRepository.findByIdAndParty(id, partyRepository.findOne(partyName));
    }

    public void deletePicture(String partyName, Long id) {
        pictureRepository.delete(getPicture(partyName,id));
    }
}
