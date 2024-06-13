package Dominio;

public class Ruta {

//lista enlazada
    private NodoPunto cabeza;
    public Ruta (){
        this.cabeza = null;
    }

    public NodoPunto getCabeza() {
        return cabeza;
    }

    public int size() {
        int size = 0;
        NodoPunto actual = cabeza;
        while (actual != null) {
            size++;
            actual = actual.getSiguiente();
        }
        return size;
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

    //clase punto
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
    //Nodo
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

        public NodoPunto getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoPunto siguiente) {
            this.siguiente = siguiente;
        }
        public int obtenerIndice(Punto punto) {
            NodoPunto actual = this;
            int indice = 0;
            while (actual != null) {
                if (actual.getCordenada().getX() == punto.getX() && actual.getCordenada().getY() == punto.getY()) {
                    return indice;
                }
                actual = actual.getSiguiente();
                indice++;
            }
            // Si no se encuentra el punto, devuelve -1
            return -1;
        }
    }


}
