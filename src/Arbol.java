import java.util.Iterator;

/** Operaciones básicas que debe ofrecer un árbol. */
public interface Arbol {
    int tamanio();
    boolean estaVacio();
    void vaciar();
    @SuppressWarnings("rawtypes")
    Iterator iterator();
    Object padreDe(Nodo nodo);
    Lista hijosDe(Nodo nodo);
    boolean esInterno(Nodo nodo);
    boolean esHoja(Nodo nodo);
    boolean reemplazarNodo(Nodo actual, Nodo nuevo);
}
