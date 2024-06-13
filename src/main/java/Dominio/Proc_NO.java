package Dominio;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import java.util.concurrent.Semaphore;
public class Proc_NO implements Runnable {
    private Semaphore sem_EO;
    private Semaphore sem_NO;
    private Semaphore sem_Interseccion;
    private Semaphore sem_Control; // Controla acceso a cantidad de carros en espera
    private Semaphore[] semaforosPuntosRuta;
    private Semaphore puntos;
    private int carrosEO;
    private int carrosNO;
    private static int carrosEsperando = 0;

    private ImageView carroNOimagen;
    private Ruta rutaNO;

    public Proc_NO(Semaphore sem_EO, Semaphore sem_NO, Semaphore sem_Interseccion, int carrosEO, int carrosNO, ImageView carroNOimagen, Ruta rutaNO) {
        this.sem_EO = sem_EO;
        this.sem_NO = sem_NO;
        this.sem_Interseccion = sem_Interseccion;
        this.carrosEO = carrosEO;
        this.carrosNO = carrosNO;
        this.carroNOimagen = carroNOimagen;
        this.rutaNO = rutaNO;
        sem_Control = new Semaphore(1); // Inicializamos el semáforo de control


        // Inicializar el array de semáforos para la ruta
        semaforosPuntosRuta = new Semaphore[rutaNO.size()];
        for (int i = 0; i < rutaNO.size(); i++) {
            semaforosPuntosRuta[i] = new Semaphore(1); // Inicializar cada semáforo en 1
        }
    }

    @Override
    public void run() {
        try {
            carrosNO++;
            Ruta.NodoPunto actual = rutaNO.getCabeza(); // Obtener el primer punto de la ruta
            carroNOimagen.setRotate(0);// se resetea la orientación de la imagen

//------------------------Movimiento de carro desde el inicio de la ruta hasta llegar a la interseccion-------------------//
            while (actual.getCordenada().getX() != 3 && actual.getCordenada().getY() != 350) {
                int posicionActual = rutaNO.obtenerPosicion(actual.getCordenada());
                sem_NO.acquire();
                semaforosPuntosRuta[posicionActual].acquire(); // Adquirir el semáforo del punto actual
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos

                if (posicionActual > 0) {
                    semaforosPuntosRuta[posicionActual-1].release();
                }else {
                    semaforosPuntosRuta[posicionActual].release();// Liberar el semáforo del punto actual
                }
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
                sem_NO.release();
            }
//------------------------------------------------------------------------------------------------------------------------//
            sem_NO.acquire();
            sem_Control.acquire(); // Controla el acceso a la variable carrosEsperando
            carrosEsperando++;
            if (carrosEsperando == 1) {
                sem_Interseccion.acquire();
            }
            sem_Control.release();
            sem_NO.release();
//-----------------------------------------Movimiento de carro por  la interseccion--------------------------------------//
            while (actual.getCordenada().getX() != -100 && actual.getCordenada().getY() != 101) {
                int posicionActual = rutaNO.obtenerPosicion(actual.getCordenada());

                semaforosPuntosRuta[posicionActual].acquire();
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                if (actual.getCordenada().getX() == -300 && actual.getCordenada().getY() == 601) {
                    carroNOimagen.setRotate(90);
                }

                semaforosPuntosRuta[posicionActual].release();

                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
            }
//------------------------------------------------------------------------------------------------------------------------//

            sem_NO.acquire();
            sem_Control.acquire(); // Controla el acceso a la variable carrosEsperando
            carrosEsperando--;
            if (carrosEsperando == 0) {
                sem_Interseccion.release();
            }
            sem_Control.release();
            sem_NO.release();
//----------------------------Movimiento de carro desde salida de la interseccion hasta fin de ruta----------------------//
            while (actual != null) {
                int posicionActual = rutaNO.obtenerPosicion(actual.getCordenada());

                semaforosPuntosRuta[posicionActual].acquire();
                Ruta.NodoPunto finalActual = actual;
                carroNOimagen.setRotate(90);
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos

                semaforosPuntosRuta[posicionActual].release();
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
