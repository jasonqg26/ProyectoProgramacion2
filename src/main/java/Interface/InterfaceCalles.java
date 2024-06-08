package Interface;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class InterfaceCalles {
    public Scene getScene (){

    BorderPane bP_Principal = new BorderPane(); // se instancia el contendor//

        // se le coloca  imagen de intersección como fondo a la escena
        Image img_fondo = new Image("DibujoInterfaz.png");

        BackgroundImage bImg = new BackgroundImage(img_fondo,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(200, 200, true, true, true, true));
        Background bGround = new Background(bImg);
        bP_Principal.setBackground(bGround);

        // Crear vBox para contener los botones
        VBox vB_Botones = new VBox(10); // Espaciado vertical entre los botones
        vB_Botones.setPadding(new Insets(150)); // Espaciado alrededor del VBox

        // se crean botones y se les asigna un tamaño
        Button btnCarroEO = new Button("Generar Carro EO");
        Button btnCarroEN = new Button("Generar Carro EN");
        Button btnCarroNO = new Button("Generar Carro NO");
        btnCarroEO.setPrefSize(150, 40);
        btnCarroEN.setPrefSize(150, 40);
        btnCarroNO.setPrefSize(150, 40);

        // Añadir los botones al VBox
        vB_Botones.getChildren().addAll(btnCarroEO, btnCarroEN, btnCarroNO);
        // se coloca el contenedor de botones a la derecha del contenedor principal
        bP_Principal.setRight(vB_Botones);


        return new Scene(bP_Principal, 1700, 900);
    }
}
