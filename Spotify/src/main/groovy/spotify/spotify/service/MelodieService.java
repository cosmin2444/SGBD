package spotify.spotify.service;

import spotify.spotify.domain.Album;
import spotify.spotify.domain.Melodie;
import spotify.spotify.observer.Event;
import spotify.spotify.observer.EventType;
import spotify.spotify.observer.Observable;
import spotify.spotify.observer.Observer;
import spotify.spotify.repository.AlbumRepository;
import spotify.spotify.repository.ArtistRepository;
import spotify.spotify.repository.MelodieRepository;

import java.util.ArrayList;
import java.util.List;

public class MelodieService implements Observable {
    private MelodieRepository  melodieRepository;
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private List<Observer> observers=new ArrayList<>();

    public MelodieService(MelodieRepository melodieRepository, AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.melodieRepository = melodieRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    public void add(Melodie melodie){
        melodieRepository.add(melodie);
        notifyObservers(new Event(EventType.Melodie));
    }

    public void delete(Long id){
        melodieRepository.delete(id);
        notifyObservers(new Event(EventType.Melodie));
    }

    public void update(Melodie melodie){
        melodieRepository.update(melodie);
        notifyObservers(new Event(EventType.Melodie));
    }

    public List<Melodie> findAll(){
        return melodieRepository.findAll();
    }

    public String findNumeAlbum(Long id_album){
        Album album = albumRepository.findOne(id_album);
        if(album!=null){
            return album.getDenumire();
        }
        return null;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Event event) {
        observers.forEach(o->{
            o.update(event);
        });
    }
}
