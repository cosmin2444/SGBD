package spotify.spotify.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import spotify.spotify.controller.message.MessageAlert;
import spotify.spotify.domain.Melodie;
import spotify.spotify.exceptions.RepositoryException;
import spotify.spotify.observer.Event;
import spotify.spotify.observer.EventType;
import spotify.spotify.observer.Observer;
import spotify.spotify.service.MelodieService;

public class MelodieAdminController implements Observer {
    private ObservableList<Melodie> model= FXCollections.observableArrayList();
    private MelodieService melodieService;

    @FXML
    private TableView<Melodie> table;

    @FXML
    private TableColumn<Melodie,Long> idColumn;

    @FXML
    private TableColumn<Melodie,String> denumireColumn;

    @FXML
    private TableColumn<Melodie,Integer> anAparitieColumn;

    @FXML
    private TableColumn<Melodie,Long> idAlbumColumn;


    @FXML
    public void initialize(){
        table.setItems(model);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        denumireColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        anAparitieColumn.setCellValueFactory(new PropertyValueFactory<>("an_aparitie"));
        idAlbumColumn.setCellValueFactory(new PropertyValueFactory<>("id_album"));
    }

    public void setUp(MelodieService melodieService){
        this.melodieService = melodieService;
        melodieService.addObserver(this);
        load();
    }

    public void load(){
        model.clear();
        model.addAll(melodieService.findAll());
    }


    public void onAdd(ActionEvent actionEvent){

    }

    public void onDelete(ActionEvent actionEvent){

    }

    public void onRefresh(ActionEvent actionEvent){
        load();
    }

    public void onNumeAlbum(ActionEvent actionEvent){
        try{
            Melodie melodie=table.getSelectionModel().getSelectedItem();
            if(melodie!=null){
                String nume = melodieService.findNumeAlbum(melodie.getId_album());
                if(nume!=null){
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Nume album","Melodia face parte din albumul: " + nume);
                }else{
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Nume album","Melodia nu face parte dintr-un album!");
                }
            }else{
                MessageAlert.showErrorMessage(null,"Selectati o melodie mai intai!");
            }
        }catch (RepositoryException r){
            MessageAlert.showErrorMessage(null,"RepositoryException: "+r.getMessage());
        }
    }


    @Override
    public void update(Event event) {
        if(event.getType().equals(EventType.Melodie))
            load();
    }
}
