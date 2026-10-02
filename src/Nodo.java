/** Representa cada elemento dentro del árbol. */
public class Nodo {
    Object valor;
    Nodo hijoIzquierdo;
    Nodo hijoDerecho;

    public Nodo(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
