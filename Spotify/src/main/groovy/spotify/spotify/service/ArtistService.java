package spotify.spotify.service;

import spotify.spotify.domain.Artist;
import spotify.spotify.observer.Event;
import spotify.spotify.observer.EventType;
import spotify.spotify.observer.Observable;
import spotify.spotify.observer.Observer;
import spotify.spotify.repository.ArtistRepository;

import java.util.ArrayList;
import java.util.List;

public class ArtistService implements Observable {
    private List<Observer> observers=new ArrayList<>();
    private ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void add(Artist artist){
        artistRepository.add(artist);
        notifyObservers(new Event(EventType.Artist));
    }

    public void delete(Long id){
        artistRepository.delete(id);
        notifyObservers(new Event(EventType.Artist));
    }

    public void update(Artist artist){
        artistRepository.update(artist);
        notifyObservers(new Event(EventType.Artist));
    }

    public List<Artist> findAll(){
        return artistRepository.findAll();
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
