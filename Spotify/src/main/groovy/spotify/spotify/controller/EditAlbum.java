package spotify.spotify.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import spotify.spotify.controller.message.MessageAlert;
import spotify.spotify.domain.Album;
import spotify.spotify.domain.Artist;
import spotify.spotify.exceptions.RepositoryException;
import spotify.spotify.service.AlbumService;
import spotify.spotify.service.ArtistService;

import java.time.LocalDateTime;

public class EditAlbum {

    @FXML
    private TextField denumire;

    @FXML
    private Spinner<Integer> an_aparitie;

    private Stage dialogStage;

    private AlbumService albumService;

    private Album album;

    @FXML
    public void initialize(){
        //Initializam Spinner-ul astfel incat sa nu suporte litere si sa aiba valorile cuprinse intre 1900 si anul curent
        SpinnerValueFactory<Integer> valueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, LocalDateTime.now().getYear(),LocalDateTime.now().getYear());
        valueFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value == null ? "" : value.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    if (string == null || string.trim().isEmpty()) {
                        return 1900; // sau o valoare default
                    }
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    MessageAlert.showErrorMessage(null,"Anul nu poate fi format din litere!");
                    return an_aparitie.getValue();
                }
            }
        });
        an_aparitie.setValueFactory(valueFactory);
        an_aparitie.setEditable(true);
    }

    public void init(AlbumService albumService,Stage stage,Album album){
        this.albumService = albumService;
        this.dialogStage = stage;
        this.album = album;

        if(album!=null){
            denumire.setText(album.getDenumire());
            an_aparitie.getValueFactory().setValue(album.getAn_aparitie());
        }
    }

    public void onSave(){
        //Verificam sa nu fie campurile goale
        if(this.denumire.getText().isEmpty() || this.an_aparitie.getValue()==null) {
            MessageAlert.showErrorMessage(null,"Toate campurile trebuie completate!");
        }
        else {
            try {
                String denumire = this.denumire.getText();
                int an_aparitie = this.an_aparitie.getValue();
                //Daca nu avem un album, inseamna ca vrem sa adaugam unul nou. In caz contrar, actualizam albumul deja existent
                // si precompletam field-urile cu datele vechi.
                if (this.album == null) {
                    Album album = new Album(denumire, an_aparitie);
                    try {
                        albumService.add(album);
                        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add", "Adaugare realizata cu succes!");
                        dialogStage.close();
                    } catch (RepositoryException r) {
                        MessageAlert.showErrorMessage(null, r.getMessage());
                    }
                } else {
                    album.setDenumire(denumire);
                    album.setAn_aparitie(an_aparitie);
                    try {
                        albumService.update(album);
                        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Update", "Actualizare realizata cu succes!");
                        dialogStage.close();
                    } catch (RepositoryException r) {
                        //Tratam exceptiile de repository prin afisarea mesajelor corespunzatoare
                        MessageAlert.showErrorMessage(null, r.getMessage());
                    }
                }
            }catch(NumberFormatException n){
                //Pentru orice eventualitate prindem si aici exceptia
                MessageAlert.showErrorMessage(null,"Anul trebuie sa fie un numar cuprins intre 1900 si data curenta!");
            }
        }
    }

    public void onCancel(){
            dialogStage.close();
        }
}
