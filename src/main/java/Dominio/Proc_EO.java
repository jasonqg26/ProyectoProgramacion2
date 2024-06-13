package Dominio;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import java.util.concurrent.Semaphore;
public class Proc_EO implements Runnable {
    private Semaphore sem_EO;
    private Semaphore sem_NO;
    private Semaphore sem_Interseccion;
    private Semaphore sem_Control; //Controla acceso a cantidad de carros en espera
    private Semaphore[] semaforosPuntosRutaEO;

    private int carrosEO;
    private int carrosNO;
    private static int carrosEsperando = 0;

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
        sem_Control = new Semaphore(1); // Inicializamos el semáforo de control
        semaforosPuntosRutaEO = new Semaphore[rutaEO.size()];
        for (int i = 0; i < rutaEO.size(); i++) {
            semaforosPuntosRutaEO[i] = new Semaphore(1); // Inicializar cada semáforo en 1
        }
    }

    @Override
    public void run() {
        try {
            carrosEO++;
            Ruta.NodoPunto actual = rutaEO.getCabeza(); // Obtener el primer punto de la ruta
 //------------------------Movimiento de carro desde el inicio de la ruta hasta llegar a la interseccion-------------------//
            while (actual.getCordenada().getX() != -600 && actual.getCordenada().getY() != 1) {
                int posicionActual = rutaEO.obtenerPosicion(actual.getCordenada());
                sem_EO.acquire();
                semaforosPuntosRutaEO[posicionActual].acquire();
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                if (posicionActual > 0) {
                    semaforosPuntosRutaEO[posicionActual-1].release();
                }else {
                    semaforosPuntosRutaEO[posicionActual].release();// Liberar el semáforo del punto actual
                }
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
                sem_EO.release();
            }
//------------------------------------------------------------------------------------------------------------------------//
            sem_EO.acquire();
            sem_Control.acquire(); // Controla el acceso a la variable carrosEsperando
            carrosEsperando++;
            if (carrosEsperando == 1) {
                sem_Interseccion.acquire(); // El primer carro adquiere el semáforo
            }
            sem_Control.release();
            sem_EO.release();

//-----------------------------------------Movimiento de carro por  la interseccion--------------------------------------//
            while (actual != null) {
                int posicionActual = rutaEO.obtenerPosicion(actual.getCordenada());
                semaforosPuntosRutaEO[posicionActual].acquire();
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                semaforosPuntosRutaEO[posicionActual].release();
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
            }
//------------------------------------------------------------------------------------------------------------------------//
            sem_EO.acquire();
            sem_Control.acquire(); // Controla el acceso a la variable carrosEsperando
            carrosEsperando--;
            if (carrosEsperando == 0) {
                sem_Interseccion.release(); // El último carro libera el semáforo
            }
            sem_Control.release();
            sem_EO.release();

//----------------------------Movimiento de carro desde salida de la interseccion hasta fin de ruta----------------------//
            while (actual != null) {
                int posicionActual = rutaEO.obtenerPosicion(actual.getCordenada());
                semaforosPuntosRutaEO[posicionActual].acquire();
                Ruta.NodoPunto finalActual = actual;
                Platform.runLater(() -> moverCarro(finalActual.getCordenada().getX(), finalActual.getCordenada().getY()));
                Thread.sleep(500); // Simular el tiempo de movimiento entre puntos
                semaforosPuntosRutaEO[posicionActual].release();
                actual = actual.getSiguiente(); // Mover al siguiente punto en la ruta
            }
//------------------------------------------------------------------------------------------------------------------------//
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

