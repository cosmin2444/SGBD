package spotify.spotify.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import spotify.spotify.service.AlbumService;
import spotify.spotify.service.ArtistService;
import spotify.spotify.service.MelodieService;

import java.io.IOException;

public class AdminController {
    private ArtistService artistService;
    private AlbumService albumService;
    private MelodieService melodieService;

    public void setUp(ArtistService artistService, AlbumService albumService,MelodieService melodieService){
        this.artistService = artistService;
        this.albumService = albumService;
        this.melodieService = melodieService;
    }

    public void onArtisti(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/spotify/spotify/artist-admin-controller.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage =  new Stage();
        stage.setTitle("Artisti");
        stage.setScene(scene);

        ArtistAdminController artistAdminController = loader.getController();
        artistAdminController.setUp(artistService);

        stage.setOnCloseRequest(event->{
            artistService.removeObserver(artistAdminController);
        });

        stage.show();
    }

    public void onAlbume(ActionEvent actionEvent)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/spotify/spotify/album-admin-controller.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage =  new Stage();
        stage.setTitle("Albume");
        stage.setScene(scene);

        AlbumAdminController albumAdminController = loader.getController();
        albumAdminController.setUp(albumService,melodieService);

        stage.setOnCloseRequest(event -> {
            albumService.removeObserver(albumAdminController);
            melodieService.removeObserver(albumAdminController);
        });

        stage.show();
    }

    public void onMelodie(ActionEvent actionEvent)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/spotify/spotify/melodie-admin-controller.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage =  new Stage();
        stage.setTitle("Melodii");
        stage.setScene(scene);

        MelodieAdminController melodieAdminController = loader.getController();
        melodieAdminController.setUp(melodieService);

        stage.setOnCloseRequest(event -> {
            melodieService.removeObserver(melodieAdminController);
        });

        stage.show();
    }
}
