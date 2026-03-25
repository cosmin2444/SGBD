package spotify.spotify.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spotify.spotify.controller.message.MessageAlert;
import spotify.spotify.domain.Melodie;
import spotify.spotify.exceptions.RepositoryException;
import spotify.spotify.service.MelodieService;

import java.time.LocalDateTime;

public class EditMelodie {
    @FXML
    private TextField denumire;

    @FXML
    private Spinner<Integer> an_aparitie;

    @FXML
    private TextField id_album;

    private Stage dialogStage;

    private MelodieService melodieService;

    private Melodie melodie;

    @FXML
    public void initialize(){
        SpinnerValueFactory<Integer> valueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, LocalDateTime.now().getYear(),LocalDateTime.now().getYear());
        an_aparitie.setValueFactory(valueFactory);
        an_aparitie.setEditable(true);
    }

    public void init(MelodieService melodieService,Stage stage,Melodie melodie){
        this.melodieService = melodieService;
        this.dialogStage = stage;
        this.melodie = melodie;

        if(melodie!=null){
            denumire.setText(melodie.getDenumire());
            an_aparitie.getValueFactory().setValue(melodie.getAn_aparitie());
            id_album.setText(melodie.getId_album().toString());
        }
    }

    public void onSave(){
        String denumire = this.denumire.getText();
        int an_aparitie = this.an_aparitie.getValue();
        Long id_album=Long.parseLong(this.id_album.getText());
        if(this.melodie==null) {
            Melodie melodie = new Melodie(denumire,an_aparitie,id_album);
            try {
                melodieService.add(melodie);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Add","Adaugare realizata cu succes!");
                dialogStage.close();
            } catch (RepositoryException r) {
                MessageAlert.showErrorMessage(null, r.getMessage());
            }
        }
        else{
            melodie.setDenumire(denumire);
            melodie.setAn_aparitie(an_aparitie);
            melodie.setId_album(id_album);
            try {
                melodieService.update(melodie);
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
