package Dominio;

import java.util.concurrent.Semaphore;

public class Proc_EN implements Runnable{
    private Semaphore sem_EO ;
    private Semaphore sem_NO ;
    private Semaphore sem_Interseccion;
    int carrosEO;
    int carrosNO;


    public Proc_EN(Semaphore sem_EO, Semaphore sem_NO, Semaphore sem_Interseccion, int carrosEO, int carrosNO) {
        this.sem_EO = sem_EO;
        this.sem_NO = sem_NO;
        this.sem_Interseccion = sem_Interseccion;
        this.carrosEO = carrosEO;
        this.carrosNO = carrosNO;
    }

    public void run() {
        while (true) {
            try {

                System.out.println("Carro llegando intersección de Este a Norte");
                System.out.println("Carro cruzando intersección de Este a Norte");
                Thread.sleep(1000); // Simulando el tiempo entre cruces
                System.out.println("Soy el carro que viene del este, ya llegue al norte");


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
