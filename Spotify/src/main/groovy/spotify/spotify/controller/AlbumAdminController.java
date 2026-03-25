package spotify.spotify.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import spotify.spotify.controller.message.MessageAlert;
import spotify.spotify.domain.Album;
import spotify.spotify.domain.Artist;
import spotify.spotify.domain.Melodie;
import spotify.spotify.exceptions.RepositoryException;
import spotify.spotify.observer.Event;
import spotify.spotify.observer.EventType;
import spotify.spotify.observer.Observer;
import spotify.spotify.service.AlbumService;
import spotify.spotify.service.MelodieService;

import java.io.IOException;

public class AlbumAdminController implements Observer {
    private AlbumService albumService;
    private MelodieService melodieService;
    private ObservableList<Album> model = FXCollections.observableArrayList();
    private ObservableList<Melodie> modelMelodii = FXCollections.observableArrayList();

    @FXML
    private TableView<Album> table;

    @FXML
    private TableView<Melodie> tableMelodii;

    @FXML
    private TableColumn<Album, Long> colId;

    @FXML
    private TableColumn<Album,String> colDenumire;

    @FXML
    private TableColumn<Album,Integer> colAnAparitie;

    @FXML
    private TableColumn<Melodie, Long> melodieId;

    @FXML
    private TableColumn<Melodie,String> melodieDenumire;

    @FXML
    private TableColumn<Melodie,Integer> melodieAnAparitie;

    @FXML
    private Button btnAddMelodie;

    @FXML
    private Button btnDeleteMelodie;

    @FXML
    private Button btnRefreshMelodie;

    @FXML
    public void initialize(){
        table.setItems(model);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        colAnAparitie.setCellValueFactory(new PropertyValueFactory<>("an_aparitie"));


        tableMelodii.setItems(modelMelodii);
        melodieId.setCellValueFactory(new PropertyValueFactory<>("id"));
        melodieDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        melodieAnAparitie.setCellValueFactory(new PropertyValueFactory<>("an_aparitie"));



        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            modelMelodii.clear();
            deselectBtns();
            btnAddMelodie.setVisible(true);
            btnDeleteMelodie.setVisible(true);
            btnRefreshMelodie.setVisible(true);
            btnAddMelodie.setDisable(false);
            btnRefreshMelodie.setDisable(false);
           if(newSelection!=null){
              loadMelodii(newSelection.getId());
           }
        });

        tableMelodii.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection!=null){
                btnDeleteMelodie.setDisable(false);
            }
        });
    }

    private void deselectBtns(){
        btnAddMelodie.setVisible(false);
        btnAddMelodie.setDisable(true);
        btnDeleteMelodie.setVisible(false);
        btnDeleteMelodie.setDisable(true);
        btnRefreshMelodie.setVisible(false);
        btnRefreshMelodie.setDisable(true);
    }

    public void setUp(AlbumService albumService,MelodieService melodieService){
        this.albumService = albumService;
        this.melodieService = melodieService;
        albumService.addObserver(this);
        melodieService.addObserver(this);
        load();
    }

    public void load(){
        model.clear();
        deselectBtns();
        model.addAll(albumService.findAll());
    }

    public void loadMelodii(Long id_album){
        modelMelodii.clear();
        modelMelodii.setAll(albumService.findAllForAlbum(id_album));
    }

    public void onAddEdit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/spotify/spotify/album-edit-controller.fxml"));
        Scene scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setTitle("Add/Edit Album");
        stage.setScene(scene);

        Album album = table.getSelectionModel().getSelectedItem();
        EditAlbum controller = loader.getController();
        controller.init(albumService, stage, album);

        stage.showAndWait();
    }

    public void onDelete(ActionEvent actionEvent){
        Album album = table.getSelectionModel().getSelectedItem();
        if(album != null) {
            try {
                albumService.delete(album.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Delete","Stergere realizata cu succes!");
            } catch (RepositoryException r) {
                MessageAlert.showErrorMessage(null, "RepositoryException: " + r.getMessage());
            }
        }
        else{
            MessageAlert.showErrorMessage(null,"Selectati un album mai intai!");
        }
    }

    public void onRefresh(ActionEvent actionEvent){
        load();
    }

    public void onMelodieAddEdit(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/spotify/spotify/melodie-album-edit-controller.fxml"));
        Scene scene = new Scene(loader.load());

        var stage = new Stage();
        stage.setTitle("Add/Edit Melodie");
        stage.setScene(scene);

        Album album = table.getSelectionModel().getSelectedItem();
        Melodie melodie = tableMelodii.getSelectionModel().getSelectedItem();
        EditMelodieAlbum controller = loader.getController();
        controller.init(melodieService, stage, melodie,album);

        stage.showAndWait();
    }

    public void onMelodieDelete(ActionEvent actionEvent){
        Melodie melodie=tableMelodii.getSelectionModel().getSelectedItem();
        if(melodie!=null){
            try{
                melodieService.delete(melodie.getId());
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Delete","Stergere realizata cu succes!");
                loadMelodii(table.getSelectionModel().getSelectedItem().getId());
            }catch (RepositoryException r){
                MessageAlert.showErrorMessage(null, "RepositoryException: " + r.getMessage());
            }
        }else{
            MessageAlert.showErrorMessage(null,"Selectati o melodie mai intai!");
        }
    }

    public void onMelodieRefresh(ActionEvent actionEvent){
        btnDeleteMelodie.setDisable(true);
        loadMelodii(table.getSelectionModel().getSelectedItem().getId());
    }

    @Override
    public void update(Event event) {
        if(event.getType().equals(EventType.Album))
            load();
        if(event.getType().equals(EventType.Melodie))
            loadMelodii(table.getSelectionModel().getSelectedItem().getId());
    }
}
