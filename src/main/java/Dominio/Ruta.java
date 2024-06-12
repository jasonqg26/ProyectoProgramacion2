package Dominio;

public class Ruta {

    public static class Punto {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Punto(int x, int y) {
            this.x = x;
            this.y = y;


        }
    }
    public static class NodoPunto {

        private Punto punto;
        private NodoPunto siguiente;

        public NodoPunto (Punto cordenada){

            this.punto = cordenada;
            this.siguiente = null;
        }

        public Punto getCordenada() {
            return punto;
        }

        public void setCordenada(Punto cordenada) {
            this.punto = cordenada;
        }

        public NodoPunto getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoPunto siguiente) {
            this.siguiente = siguiente;
        }
    }


    private NodoPunto cabeza;
    public Ruta (){
        this.cabeza = null;
    }

    public NodoPunto getCabeza() {
        return cabeza;
    }


    public void insertar(Punto punto) {
        NodoPunto nuevoPunto = new NodoPunto(punto);
        if (cabeza == null) {
            cabeza = nuevoPunto;
        } else {
            NodoPunto actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoPunto);
        }
    }
}
