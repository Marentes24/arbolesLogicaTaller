/** Representa cada elemento dentro del árbol. */
public class ArbolesLogicaTallerNodo {
    Object valor;
    ArbolesLogicaTallerNodo hijoIzquierdo;
    ArbolesLogicaTallerNodo hijoDerecho;

    public ArbolesLogicaTallerNodo(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
