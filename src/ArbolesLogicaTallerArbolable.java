import java.util.Iterator;

/** Operaciones básicas que debe ofrecer un árbol. */
public interface ArbolesLogicaTallerArbolable {
    int tamanio();
    boolean estaVacio();
    void vaciar();
    @SuppressWarnings("rawtypes")
    Iterator iterator();
    Object padreDe(ArbolesLogicaTallerNodo nodo);
    ArbolesLogicaTallerLista hijosDe(ArbolesLogicaTallerNodo nodo);
    boolean esInterno(ArbolesLogicaTallerNodo nodo);
    boolean esHoja(ArbolesLogicaTallerNodo nodo);
    boolean reemplazarNodo(ArbolesLogicaTallerNodo actual, ArbolesLogicaTallerNodo nuevo);
}
