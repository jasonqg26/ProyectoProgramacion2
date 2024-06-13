package Interface;


import Dominio.Proc_EN;
import Dominio.Proc_EO;
import Dominio.Proc_NO;
import Dominio.Ruta;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.concurrent.Semaphore;


public class InterfaceCalles {

    Semaphore sem_EO = new Semaphore(1);
    Semaphore sem_NO = new Semaphore(1);
    Semaphore sem_Interseccion = new Semaphore(1);
    int carrosEO = 0;
    int carrosNO = 0;

    public Scene getScene() {

        Pane P_Principal = new Pane(); // se instancia el contendor//
//--------------------------------------------FONDO-------------------------------------------------------------------------//
        // se le coloca  imagen de intersección como fondo a la escena
        Image img_fondo = new Image("DibujoInterfaz.png");

        BackgroundImage bImg = new BackgroundImage(img_fondo,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(200, 200, true, true, true, true));
        Background bGround = new Background(bImg);
        P_Principal.setBackground(bGround);

        // Crear vBox para contener los botones
        VBox vB_Botones = new VBox(10); // Espaciado vertical entre los botones
        vB_Botones.setPadding(new Insets(150)); // Espaciado alrededor del VBox

//--------------------------------------------CREAR BOTONES-------------------------------------------------------------------------//
        Button btnCarroEO = new Button("Generar Carro EO");
        Button btnCarroEN = new Button("Generar Carro EN");
        Button btnCarroNO = new Button("Generar Carro NO");
        btnCarroEO.setPrefSize(150, 40);
        btnCarroEN.setPrefSize(150, 40);
        btnCarroNO.setPrefSize(150, 40);

//--------------------------------------------Carro EO-------------------------------------------------------------------------//

        // por cada clic se crea un carrp nuevo
        btnCarroEO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ImageView EOImageView = new ImageView(new Image("EO.png"));
                EOImageView.setFitWidth(160); // Ancho deseado
                EOImageView.setFitHeight(200); // Alto deseado
                EOImageView.setLayoutX(1600);
                EOImageView.setLayoutY(670);
                // se añade la imagen al pane
                P_Principal.getChildren().add(EOImageView);

                //se asigna la ruta al carro
                Ruta rutaCarroEO = new Ruta();
                rutaCarroEO.insertar(new Ruta.Punto(0, 0));
                rutaCarroEO.insertar(new Ruta.Punto(-100, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-200, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-300, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-400, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-500, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-600, 1));
                rutaCarroEO.insertar(new Ruta.Punto(-700, 2));//llega a interseccion
                rutaCarroEO.insertar(new Ruta.Punto(-800, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-900, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-1000, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-1100, 2));
                rutaCarroEO.insertar(new Ruta.Punto(-1200, -101));// final interseccion
                rutaCarroEO.insertar(new Ruta.Punto(-1300, -100));
                rutaCarroEO.insertar(new Ruta.Punto(-1400, -100));
                rutaCarroEO.insertar(new Ruta.Punto(-1500, -100));
                rutaCarroEO.insertar(new Ruta.Punto(-1600, -100));
                rutaCarroEO.insertar(new Ruta.Punto(-1700, -100));
                rutaCarroEO.insertar(new Ruta.Punto(-1800, -100));
                rutaCarroEO.insertar(new Ruta.Punto(-1900, -101));

                // se ejecuta el proceso
                new Thread(new Proc_EO(sem_EO, sem_NO, sem_Interseccion, carrosEO, carrosNO, EOImageView, rutaCarroEO)).start();
            }
        });
//--------------------------------------------Carro EN-------------------------------------------------------------------------//


        btnCarroEN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ImageView ENImageView = new ImageView(new Image("EN.png"));
                ENImageView.setFitWidth(160);
                ENImageView.setFitHeight(200);
                ENImageView.setLayoutX(1600);
                ENImageView.setLayoutY(460);

                P_Principal.getChildren().add(ENImageView);


                Ruta rutaCarroEN = new Ruta();
                rutaCarroEN.insertar(new Ruta.Punto(0, 0));
                rutaCarroEN.insertar(new Ruta.Punto(-100, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-200, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-300, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-400, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-500, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-600, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-700, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-800, 2));
                rutaCarroEN.insertar(new Ruta.Punto(-800, -100));
                rutaCarroEN.insertar(new Ruta.Punto(-800, -200));
                rutaCarroEN.insertar(new Ruta.Punto(-800, -300));
                rutaCarroEN.insertar(new Ruta.Punto(-800, -400));
                rutaCarroEN.insertar(new Ruta.Punto(-800, -500));
                rutaCarroEN.insertar(new Ruta.Punto(-800, -600));
                rutaCarroEN.insertar(new Ruta.Punto(-800, -700));

                new Thread(new Proc_EN(ENImageView, rutaCarroEN)).start();


            }
        });
//--------------------------------------------Carro NO-------------------------------------------------------------------------//

        btnCarroNO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ImageView NOImageView = new ImageView(new Image("NO.png"));
                NOImageView.setFitWidth(100);
                NOImageView.setFitHeight(160);
                NOImageView.setLayoutX(600);
                NOImageView.setLayoutY(-50);

                P_Principal.getChildren().add(NOImageView);


                Ruta rutaCarroNO = new Ruta();
                rutaCarroNO.insertar(new Ruta.Punto(0, 0));
                rutaCarroNO.insertar(new Ruta.Punto(2, 100));
                rutaCarroNO.insertar(new Ruta.Punto(2, 200));
                rutaCarroNO.insertar(new Ruta.Punto(2, 300));
                rutaCarroNO.insertar(new Ruta.Punto(3, 350));//llegada a la interseccion
                rutaCarroNO.insertar(new Ruta.Punto(2, 400));
                rutaCarroNO.insertar(new Ruta.Punto(2, 500));
                rutaCarroNO.insertar(new Ruta.Punto(-100, 600));
                rutaCarroNO.insertar(new Ruta.Punto(-200, 600));
                rutaCarroNO.insertar(new Ruta.Punto(-300, 601));
                rutaCarroNO.insertar(new Ruta.Punto(-400, 600));
                rutaCarroNO.insertar(new Ruta.Punto(-500, 600));
                rutaCarroNO.insertar(new Ruta.Punto(-600, 600));
                rutaCarroNO.insertar(new Ruta.Punto(-700, 600));
                rutaCarroNO.insertar(new Ruta.Punto(-800, 600));

                new Thread(new Proc_NO(sem_EO, sem_NO, sem_Interseccion, carrosEO, carrosNO, NOImageView, rutaCarroNO)).start();


            }
        });
//--------------------------------------------AÑADIR A CONTENEDORES-------------------------------------------------------------------------//


        // Añadir los botones al VBox
        vB_Botones.getChildren().addAll(btnCarroEO, btnCarroEN, btnCarroNO);
        // ubicacion del vBox
        vB_Botones.setLayoutX(1000);
        vB_Botones.setLayoutY(100);
        // se agregan el vbox al contenedor
        P_Principal.getChildren().addAll(vB_Botones);




        return new Scene(P_Principal, 1700, 900);
    }

}