package Dominio;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.concurrent.Semaphore;

public class Proc_EO implements Runnable{
    private Semaphore sem_EO ;
    private Semaphore sem_NO ;
    private Semaphore sem_Interseccion;
    int carrosEO;
    int carrosNO;
    private ImageView carroEOimagen;
    private Ruta rutaEO;


    public Proc_EO(Semaphore sem_EO, Semaphore sem_NO, Semaphore sem_Interseccion, int carrosEO, int carrosNO, ImageView carroEOimagen, Ruta rutaEO) {
        this.sem_EO = sem_EO;
        this.sem_NO = sem_NO;
        this.sem_Interseccion = sem_Interseccion;
        this.carrosEO = carrosEO;
        this.carrosNO = carrosNO;
        this.carroEOimagen = carroEOimagen;
        this.rutaEO = rutaEO;
    }

    @Override
    public void run() {

            try {
                carrosEO++;
                Ruta.NodoPunto actual = rutaEO.getCabeza(); // Obtener el primer punto de la ruta

                //mover el carro hasta llegar a la interseccion
            while (actual.getCordenada().getX() != -600 && actual.getCordenada().getY() != 1 ) {
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
            }
                sem_EO.acquire();


                if (carrosEO == 1){
                    sem_Interseccion.acquire();
                }

                sem_EO.release();
                while (actual.getCordenada().getX() != -1200 && actual.getCordenada().getY() != -101 ) {
                    Ruta.NodoPunto finalActual = actual;
                    Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                    Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                    actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
                }
                sem_EO.acquire();

                carrosEO--;
                if (carrosEO == 0){
                    sem_Interseccion.release();
                }
                sem_EO.release();
                while (actual != null) {
                    Ruta.NodoPunto finalActual = actual;
                    Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                    Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                    actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }




    private void moverCarro(double x, double y) {
        carroEOimagen.setVisible(true);
        carroEOimagen.setTranslateX(x);
        carroEOimagen.setTranslateY(y);
    }

}