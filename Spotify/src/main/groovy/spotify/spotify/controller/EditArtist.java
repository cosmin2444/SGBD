package spotify.spotify.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spotify.spotify.controller.message.MessageAlert;
import spotify.spotify.domain.Artist;
import spotify.spotify.exceptions.RepositoryException;
import spotify.spotify.service.ArtistService;

public class EditArtist {
    @FXML
    private TextField nume_scena;

    private Stage dialogStage;

    private ArtistService artistService;

    private Artist artist;

    public void init(ArtistService artistService,Stage stage,Artist artist){
        this.artistService = artistService;
        this.dialogStage = stage;
        this.artist = artist;
        if(artist!=null){
            nume_scena.setText(artist.getNume_scena());
        }
    }

    public void onSave(){
        String nume_scena = this.nume_scena.getText();
        if(this.artist==null) {
            Artist artist = new Artist(nume_scena);
            try {
                artistService.add(artist);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Add","Adaugare realizata cu succes!");
                dialogStage.close();
            } catch (RepositoryException r) {
                MessageAlert.showErrorMessage(null, r.getMessage());
            }
        }
        else{
            artist.setNume_scena(nume_scena);
            try {
                artistService.update(artist);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Update","Actualizare realizata cu succes!");
                dialogStage.close();
            } catch (RepositoryException r) {
                MessageAlert.showErrorMessage(null, r.getMessage());
            }
        }
    }

    public void onCancel(){
        dialogStage.close();
    }
}
