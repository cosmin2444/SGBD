package spotify.spotify.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spotify.spotify.controller.message.MessageAlert;
import spotify.spotify.domain.Album;
import spotify.spotify.domain.Melodie;
import spotify.spotify.exceptions.RepositoryException;
import spotify.spotify.service.MelodieService;

import java.time.LocalDateTime;

public class EditMelodieAlbum {
    @FXML
    private TextField denumire;

    private Stage dialogStage;

    private MelodieService melodieService;

    private Melodie melodie;

    private Album album;

    @FXML
    public void initialize(){
    }

    public void init(MelodieService melodieService,Stage stage,Melodie melodie,Album album){
        this.melodieService = melodieService;
        this.dialogStage = stage;
        this.melodie = melodie;
        this.album = album;

        if(melodie!=null){
            denumire.setText(melodie.getDenumire());
        }
    }

    public void onSave(){
        if(this.denumire.getText().isEmpty()){
            MessageAlert.showErrorMessage(null,"Toate campurile trebuie completate!");
        }
        else {
            String denumire = this.denumire.getText();
            if (this.melodie == null) {
                Melodie melodie = new Melodie(denumire, album.getAn_aparitie(), album.getId());
                try {
                    melodieService.add(melodie);
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Add", "Adaugare realizata cu succes!");
                    dialogStage.close();
                } catch (RepositoryException r) {
                    MessageAlert.showErrorMessage(null, r.getMessage());
                }
            } else {
                melodie.setDenumire(denumire);
                try {
                    melodieService.update(melodie);
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Update", "Actualizare realizata cu succes!");
                    dialogStage.close();
                } catch (RepositoryException r) {
                    MessageAlert.showErrorMessage(null, r.getMessage());
                }
            }
        }
    }

    public void onCancel(){
        dialogStage.close();
    }
}
