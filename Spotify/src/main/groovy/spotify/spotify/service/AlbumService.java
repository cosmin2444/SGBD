package spotify.spotify.service;

import spotify.spotify.domain.Album;
import spotify.spotify.domain.Melodie;
import spotify.spotify.observer.Event;
import spotify.spotify.observer.EventType;
import spotify.spotify.observer.Observable;
import spotify.spotify.observer.Observer;
import spotify.spotify.repository.AlbumRepository;
import spotify.spotify.repository.MelodieRepository;

import java.util.ArrayList;
import java.util.List;

public class AlbumService implements Observable {
    private List<Observer> observers=new ArrayList<>();
    private AlbumRepository albumRepository;
    private MelodieRepository melodieRepository;

    public AlbumService(AlbumRepository albumRepository, MelodieRepository melodieRepository) {
        this.albumRepository = albumRepository;
        this.melodieRepository = melodieRepository;
    }

    public void add(Album album){
        albumRepository.add(album);
        notifyObservers(new Event(EventType.Album));
    }

    public void delete(Long id){
        albumRepository.delete(id);
        notifyObservers(new Event(EventType.Album));
    }

    public void update(Album album){
        albumRepository.update(album);
        notifyObservers(new Event(EventType.Album));
    }

    public List<Album> findAll(){
        return albumRepository.findAll();
    }

    public List<Melodie> findAllForAlbum(Long id_album){
        return melodieRepository.findAllForAlbum(id_album);
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
