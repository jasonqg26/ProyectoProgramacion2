package Dominio;

import java.util.concurrent.Semaphore;

// Autores: Alexis Fabián Quesada Cordero C36202 y Jason Quesada Gómez C36213
public class Main {
    public static void main(String[] args)  {


        Semaphore sem_EO = new Semaphore(1);
        Semaphore sem_NO = new Semaphore(1);
        Semaphore sem_Interseccion = new Semaphore(1);
        int carrosEO = 0;
        int carrosNO = 0;
        Thread hilo_EO = new Thread(new Proc_EO(sem_EO,sem_NO,sem_Interseccion,carrosEO,carrosNO));
        Thread hilo_EN = new Thread(new Proc_EN(sem_EO,sem_NO,sem_Interseccion,carrosEO,carrosNO));
        Thread hilo_NO = new Thread(new Proc_NO(sem_EO,sem_NO,sem_Interseccion,carrosEO,carrosNO));




        hilo_EO.start();
        hilo_EN.start();
        hilo_NO.start();

//        Ruta ej = new Ruta();
//        ej.insertar(new Ruta.Punto(0,4));


    }
}