package spotify.spotify.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import spotify.spotify.controller.message.MessageAlert;
import spotify.spotify.domain.Artist;
import spotify.spotify.exceptions.RepositoryException;
import spotify.spotify.observer.Event;
import spotify.spotify.observer.EventType;
import spotify.spotify.observer.Observer;
import spotify.spotify.service.ArtistService;

import java.io.IOException;

public class ArtistAdminController implements Observer {
    private ArtistService artistService;
    private ObservableList<Artist> model=FXCollections.observableArrayList();;

    @FXML
    private TableView<Artist> tableView;

    @FXML
    private TableColumn<Artist,Long> columnId;

    @FXML
    private TableColumn<Artist,String> columnNumeScena;

    @FXML
    public void initialize(){
        tableView.setItems(model);
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNumeScena.setCellValueFactory(new PropertyValueFactory<>("nume_scena"));
    }
    public void setUp(ArtistService artistService){
        this.artistService = artistService;
        artistService.addObserver(this);
        load();
    }

    private void load(){
        model.clear();
        model.setAll(artistService.findAll());
    }

    public void onRefresh(ActionEvent event){
        load();
    }

    public void onAddEdit(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/spotify/spotify/artist-edit-controller.fxml"));
            Scene scene = new Scene(loader.load());

            var stage = new Stage();
            stage.setTitle("Add/Edit Artist");
            stage.setScene(scene);

            Artist artist = tableView.getSelectionModel().getSelectedItem();
            EditArtist controller = loader.getController();
            controller.init(artistService, stage, artist);

            stage.showAndWait();
        }catch (IOException e){
            MessageAlert.showErrorMessage(null,"IOException: " + e.getMessage());
        }
    }

    public void onDelete(ActionEvent event){
        Artist artist=tableView.getSelectionModel().getSelectedItem();
        if(artist!=null){
            try{
                artistService.delete(artist.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Delete","Stergere realizata cu succes!");
            }catch (RepositoryException e){
                MessageAlert.showErrorMessage(null,"RepositoryException: " + e.getMessage());
            }
        }
        else{
            MessageAlert.showErrorMessage(null,"Selectati un artist mai intai!");
        }
    }

    @Override
    public void update(Event event) {
        if(event.getType().equals(EventType.Artist))
            load();
    }
}
