package Dominio;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.concurrent.Semaphore;

public class Proc_EN implements Runnable{

    private ImageView carroENimagen;
    private Ruta rutaEN;

    public Proc_EN(ImageView carroENimagen, Ruta rutaEN) {
        this.carroENimagen = carroENimagen;
        this.rutaEN = rutaEN;
    }

    public void run() {

            try {
//------------------------Movimiento de carro desde  inicio de la ruta hasta llegar al final de la ruta-------------------//
                Ruta.NodoPunto actual = rutaEN.getCabeza(); // Obtener el primer punto de la ruta
                carroENimagen.setRotate(0);
                //mover el carro hasta llegar a la interseccion
                while (actual != null ) {
                    Ruta.NodoPunto finalActual = actual;
                    Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                    Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                    if (actual.getCordenada().getX() ==-800 && actual.getCordenada().getY() == 2){
                        carroENimagen.setRotate(90);
                    }
                    actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
                }
//-----------------------------------------------------------------------------------------------------------------------//

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    private void moverCarro(double x, double y) {
        carroENimagen.setVisible(true);
        carroENimagen.setTranslateX(x);
        carroENimagen.setTranslateY(y);
    }


}
