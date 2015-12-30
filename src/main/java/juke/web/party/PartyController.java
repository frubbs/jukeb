package juke.web.party;
import juke.domain.party.Party;
import juke.domain.party.PartyService;
import juke.domain.picture.Picture;
import juke.domain.song.Song;
import juke.domain.song.Vote;
import juke.exception.NotFoundException;
import juke.web.picture.PictureResoure;
import juke.web.song.SongResource;
import juke.web.song.VoteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/party")
public class PartyController {
    private @Autowired PartyService partyService;
    private @Autowired MapperFacade mapperFacade;


    /*
    Recupera uma festa corrente.
    Deve ser usado quando um CLIENTE entra pela primeira vez
     */
    @RequestMapping(method = RequestMethod.GET, value="/{party-name}")
    public PartyResource get(@PathVariable("party-name") String partyName) {
        Party party = partyService.getParty(partyName);
        PartyResource partyResource = mapperFacade.map(party, PartyResource.class);

        List<Song> songs = partyService.getSongs(partyName);

        partyResource.setPlaylist(convertToSongListResource(partyName,songs));

        builldPartyLinks(partyResource);
        return partyResource;
    }

    /*
    Cria uma festa.
    Deve ser usado apos fazer o GET na festa e obter 404
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public PartyResource createParty(@RequestBody PartyResource resource) {
        Party party = partyService.create(mapperFacade.map(resource, Party.class));
        PartyResource partyResource = mapperFacade.map(party, PartyResource.class);
        builldPartyLinks(partyResource);
        return partyResource;
    }

    private void builldPartyLinks( PartyResource partyResource) {
        partyResource.add(linkTo(methodOn(PartyController.class)
                .get(partyResource.getName())).withSelfRel());
        partyResource.add(linkTo(methodOn(PartyController.class)
                .addSong(partyResource.getName(), null)).withRel("songs"));
        partyResource.add(linkTo(methodOn(PartyController.class)
                .listPictures(partyResource.getName())).withRel("pictures"));
    }


    /*
    Adiciona uma musica no final da lista da festa.
    */
    @RequestMapping(method = RequestMethod.POST, value="/{party-name}/songs")
    @ResponseStatus(HttpStatus.CREATED)
    public SongResource addSong(@PathVariable("party-name") String partyName, @RequestBody SongResource resource){
        Song song = partyService.addSong(partyName, mapperFacade.map(resource, Song.class));
        return convertSongToResource(partyName, song);
    }


    /*
       Altera a lista de musicas.
       Basicamente alterna o status playing de uma pra outra.
       o body contem a musica que sera tocada
    */
    @RequestMapping(method = RequestMethod.PATCH, value="/{party-name}/songs")
    public Object patch(@PathVariable("party-name") String partyName, @RequestBody List<SongResource> resource){
        partyService.updatePartySongs(partyName, mapperFacade.mapAsList(resource, Song.class));

        return null;
    }


    /*
    Lista as musicas da festa
     */
    @RequestMapping(method = RequestMethod.GET, value="/{party-name}/songs")
    public List<SongResource> listSongs(@PathVariable("party-name") String partyName){
        List<Song> songs = partyService.getSongs(partyName);

        List<SongResource> songResources = convertToSongListResource(partyName, songs);

        return songResources;
    }

    private List<SongResource> convertToSongListResource(String partyName, List<Song> songs) {

        Stream<SongResource> songResources = songs.stream().map(s -> convertSongToResource(partyName, s));

        return songResources.collect(Collectors.toList());
    }


    /*
    Recupera uma musica da lista.
    Cada musica tem uma lista d(e links para )as musicas seguintes e as anteriores.
     */
    @RequestMapping(method = RequestMethod.GET, value="/{party-name}/songs/{songId}")
    public SongResource getSong(@PathVariable("party-name") String partyName, @PathVariable("songId") Long songId) {

        System.out.println("ooooooooooooooooooeeeeeeeeee");

        Song song = partyService.getSong(partyName, songId);

        SongResource songResource = convertSongToResource(partyName, song);

        return songResource;//mapperFacade.map(party, PartyResource.class);
    }

    private SongResource convertSongToResource(String partyName, Song song) {
        SongResource songResource = mapperFacade.map(song, SongResource.class);

        songResource.add(linkTo(methodOn(PartyController.class)
                .getSong(partyName, song.getId()))
                .withSelfRel());


        songResource.add(linkTo(methodOn(PartyController.class)
                .getVotes(partyName, song.getId()))
                .withRel("votes"));

        return songResource;
    }


    /*
    Registra um voto para uma musica de uma festa
    menor que 0 -> downvote
    */
    @RequestMapping(method = RequestMethod.POST, value="/{party-name}/songs/{songId}/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public Object voteSong(@PathVariable("party-name") String partyName, @PathVariable("songId") Long songId, @RequestBody VoteResource resource){
        partyService.voteSong(partyName, songId, mapperFacade.map(resource, Vote.class));

        return null;

    }

    @RequestMapping(method = RequestMethod.GET, value="/{party-name}/songs/{songId}/votes")
    public List<VoteResource> getVotes(@PathVariable("party-name") String partyName, @PathVariable("songId") Long songId){
        List<Vote> votes = partyService.getVotes(partyName, songId);

        return mapperFacade.mapAsList(votes, VoteResource.class);

    }


    @RequestMapping(value="/{party-name}/pictures", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody String uploadPicture(@PathVariable("party-name") String partyName,
                                              @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                /*BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("src/test/resources/chica2.jpg")));
                stream.write(bytes);
                stream.close();*/

                partyService.savePicture(partyName,bytes);


                return "{\"result\":\"You successfully uploaded " + partyName + "!\"}";
            } catch (Exception e) {
                throw new RuntimeException("You failed to upload " + partyName + " => " + e.getMessage());
            }
        } else {
            throw new RuntimeException("You failed to upload " + partyName + " because the file was empty.");
        }
    }

    @RequestMapping(value="/{party-name}/pictures", method=RequestMethod.GET)
    public List<PictureResoure> listPictures(@PathVariable("party-name") String partyName){
        List<Picture> pictures = partyService.getPictures(partyName);
        Stream<PictureResoure> pictureResources = pictures.stream().map(p -> convertPictureToResource(partyName, p));
        return pictureResources.collect(Collectors.toList());
    }

    public PictureResoure convertPictureToResource(String partyName, Picture p) {

        PictureResoure picResource = new PictureResoure();
        picResource.setParty(partyName);
        picResource.setId(p.getId());
/*
        picResource.add(linkTo(methodOn(PartyController.class)
                .getPicture(partyName, p.getId()))
                .withSelfRel());*/
        picResource.add(linkTo(methodOn(PartyController.class)
                .getPicture(partyName, p.getId()))
                .withSelfRel());

        picResource.add(linkTo(methodOn(PartyController.class)
                .getImage(partyName, p.getId()))
                .withRel("image"));

        return picResource;

    }

    @RequestMapping(method = RequestMethod.GET, value="/{party-name}/pictures/{id}")
    public PictureResoure getPicture(@PathVariable("party-name") String partyName, @PathVariable("id") Long id) {
        try {
            Picture p = partyService.getPicture(partyName, id);
            if (p != null) {
                return convertPictureToResource(partyName, p);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        throw new NotFoundException();
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{party-name}/pictures/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PictureResoure deletePicture(@PathVariable("party-name") String partyName, @PathVariable("id") Long id) {
        try {
            partyService.deletePicture(partyName, id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        throw new NotFoundException();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{party-name}/pictures/{id}/image")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("party-name") String partyName, @PathVariable("id") Long id){

        Picture p = null;
        try {
            p = partyService.getPicture(partyName, id);
            if (p == null) {
                throw new NotFoundException();
            }

            File gridFsFile = p.getFile();

            FileInputStream fs = new FileInputStream(gridFsFile);
            return ResponseEntity.ok()
                    //.contentLength(fs.getLength())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(fs));

        }
        catch(NotFoundException e){

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}