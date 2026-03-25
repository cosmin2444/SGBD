package spotify.spotify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import spotify.spotify.controller.AdminController;
import spotify.spotify.repository.AlbumRepository;
import spotify.spotify.repository.ArtistRepository;
import spotify.spotify.repository.MelodieRepository;
import spotify.spotify.service.AlbumService;
import spotify.spotify.service.ArtistService;
import spotify.spotify.service.MelodieService;

import java.io.IOException;
import java.util.Properties;

public class AdminApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Properties props = new Properties();
        try (var is = getClass().getResourceAsStream("bd.config")) {
            if (is == null) {
                throw new IOException("Cannot find bd.config in the classpath");
            }
            props.load(is);
        } catch (IOException e) {
            System.err.println("Error loading config: " + e.getMessage());
            return;
        }

        ArtistRepository artistRepository = new ArtistRepository(props);
        AlbumRepository albumRepository = new AlbumRepository(props);
        MelodieRepository melodieRepository = new MelodieRepository(props);


        ArtistService artistService = new ArtistService(artistRepository);
        AlbumService  albumService = new AlbumService(albumRepository,melodieRepository);
        MelodieService melodieService = new MelodieService(melodieRepository,albumRepository,artistRepository);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-controller.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Admin Application");
        stage.setScene(scene);

        AdminController controller = loader.getController();
        controller.setUp(artistService, albumService,melodieService);

        stage.show();
    }
}
