package Interface;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        InterfaceCalles interfaceCalles = new InterfaceCalles();

        stage.getIcons().add(new Image("icono_app.png"));
        stage.setTitle("Simulación de intersección");
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(interfaceCalles.getScene());
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}