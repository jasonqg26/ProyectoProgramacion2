package Dominio;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import java.util.concurrent.Semaphore;
public class Proc_NO implements Runnable {
    private Semaphore sem_EO;
    private Semaphore sem_NO;
    private Semaphore sem_Interseccion;
    private Semaphore sem_Control; // Controla acceso a cantidad de carros en espera

    private static int carrosNO;

    private ImageView carroNOimagen;
    private Ruta rutaNO;

    public Proc_NO(Semaphore sem_EO, Semaphore sem_NO, Semaphore sem_Interseccion, int carrosNO, ImageView carroNOimagen, Ruta rutaNO) {
        this.sem_EO = sem_EO;
        this.sem_NO = sem_NO;
        this.sem_Interseccion = sem_Interseccion;

        this.carrosNO = carrosNO;
        this.carroNOimagen = carroNOimagen;
        this.rutaNO = rutaNO;
        sem_Control = new Semaphore(1); // Inicializamos el semáforo de control
    }

    @Override
    public void run() {
        try {

            Ruta.NodoPunto actual = rutaNO.getCabeza(); // Obtener el primer punto de la ruta
            carroNOimagen.setRotate(0);// se resetea la orientación de la imagen

//------------------------Movimiento de carro desde el inicio de la ruta hasta llegar a la interseccion-------------------//
            while (actual.getCordenada().getX() != 3 && actual.getCordenada().getY() != 350) {
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
            }
//------------------------------------------------------------------------------------------------------------------------//
            sem_NO.acquire();
            sem_Control.acquire(); // Controla el acceso a la variable carrosEsperando
            carrosNO++;
            if (carrosNO == 1) {
                sem_Interseccion.acquire();
            }
            sem_Control.release();
            sem_NO.release();
//-----------------------------------------Movimiento de carro por  la interseccion--------------------------------------//
            while (actual.getCordenada().getX() != -100 && actual.getCordenada().getY() != 101) {
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                if (actual.getCordenada().getX() == -300 && actual.getCordenada().getY() == 601) {
                    carroNOimagen.setRotate(90);
                }
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
            }
//------------------------------------------------------------------------------------------------------------------------//

            sem_NO.acquire();
            sem_Control.acquire(); // Controla el acceso a la variable carrosEsperando
            carrosNO--;
            if (carrosNO == 0) {
                sem_Interseccion.release();
            }
            sem_Control.release();
            sem_NO.release();
//----------------------------Movimiento de carro desde salida de la interseccion hasta fin de ruta----------------------//
            while (actual != null) {
                Ruta.NodoPunto finalActual = actual;
                carroNOimagen.setRotate(90);
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
            }
//------------------------------------------------------------------------------------------------------------------------//
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moverCarro(double x, double y) {
        carroNOimagen.setVisible(true);
        carroNOimagen.setTranslateX(x);
        carroNOimagen.setTranslateY(y);
    }
}
