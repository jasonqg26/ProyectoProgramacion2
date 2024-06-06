package Dominio;

import java.util.concurrent.Semaphore;

public class Proc_NO implements Runnable{


    private Semaphore sem_EO ;
    private Semaphore sem_NO ;
    private Semaphore sem_Interseccion;
    int carrosEO;
    int carrosNO;


    public Proc_NO(Semaphore sem_EO, Semaphore sem_NO, Semaphore sem_Interseccion, int carrosEO, int carrosNO) {
        this.sem_EO = sem_EO;
        this.sem_NO = sem_NO;
        this.sem_Interseccion = sem_Interseccion;
        this.carrosEO = carrosEO;
        this.carrosNO = carrosNO;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("carro llegando a interseccion de Norte a oeste");
                sem_NO.acquire();

                carrosNO++;
                if (carrosNO == 1){
                    sem_Interseccion.acquire();
                }

                sem_NO.release();
                System.out.println("Carro cruzando interseccion de Norte a Oeste");
                Thread.sleep(1000); // Simulando el tiempo entre cruces
                sem_NO.acquire();

                carrosNO--;
                if (carrosNO == 0){
                    sem_Interseccion.release();
                }
                sem_NO.release();
                System.out.println("Soy el carro que viene del Norte, ya llegue al oeste");


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
