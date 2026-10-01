import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Árbol binario de búsqueda que también implementa el TAD Arbolable. */
public class ArbolesLogicaTallerBinario implements ArbolesLogicaTallerArbolable {
    private ArbolesLogicaTallerNodo raiz;

    public void insertarValor(int dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private ArbolesLogicaTallerNodo insertarRecursivo(ArbolesLogicaTallerNodo nodo, int dato) {
        if (nodo == null) return new ArbolesLogicaTallerNodo(dato);

        int datoActual = (Integer) nodo.valor;
        if (dato < datoActual) nodo.hijoIzquierdo = insertarRecursivo(nodo.hijoIzquierdo, dato);
        else if (dato > datoActual) nodo.hijoDerecho = insertarRecursivo(nodo.hijoDerecho, dato);
        return nodo;
    }

    @Override
    public int tamanio() {
        return contarNodos(raiz);
    }

    private int contarNodos(ArbolesLogicaTallerNodo nodo) {
        return nodo == null ? 0 : 1 + contarNodos(nodo.hijoIzquierdo) + contarNodos(nodo.hijoDerecho);
    }

    @Override
    public boolean estaVacio() {
        return raiz == null;
    }

    @Override
    public void vaciar() {
        raiz = null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Iterator iterator() {
        List<ArbolesLogicaTallerNodo> nodos = new ArrayList<>();
        nodosPreorden(raiz, nodos);
        return nodos.iterator();
    }

    @Override
    public Object padreDe(ArbolesLogicaTallerNodo nodo) {
        return buscarPadre(raiz, nodo);
    }

    private ArbolesLogicaTallerNodo buscarPadre(ArbolesLogicaTallerNodo actual, ArbolesLogicaTallerNodo buscado) {
        if (actual == null || actual == buscado) return null;
        if (actual.hijoIzquierdo == buscado || actual.hijoDerecho == buscado) return actual;
        ArbolesLogicaTallerNodo encontrado = buscarPadre(actual.hijoIzquierdo, buscado);
        return encontrado != null ? encontrado : buscarPadre(actual.hijoDerecho, buscado);
    }

    @Override
    public ArbolesLogicaTallerLista hijosDe(ArbolesLogicaTallerNodo nodo) {
        ArbolesLogicaTallerLista resultado = new ArbolesLogicaTallerLista();
        if (pertenece(nodo)) {
            if (nodo.hijoIzquierdo != null) resultado.add(nodo.hijoIzquierdo);
            if (nodo.hijoDerecho != null) resultado.add(nodo.hijoDerecho);
        }
        return resultado;
    }

    @Override
    public boolean esInterno(ArbolesLogicaTallerNodo nodo) {
        return pertenece(nodo) && (nodo.hijoIzquierdo != null || nodo.hijoDerecho != null);
    }

    @Override
    public boolean esHoja(ArbolesLogicaTallerNodo nodo) {
        return pertenece(nodo) && nodo.hijoIzquierdo == null && nodo.hijoDerecho == null;
    }

    @Override
    public boolean reemplazarNodo(ArbolesLogicaTallerNodo actual, ArbolesLogicaTallerNodo nuevo) {
        if (nuevo == null || !pertenece(actual)) return false;
        actual.valor = nuevo.valor;
        return true;
    }

    private boolean pertenece(ArbolesLogicaTallerNodo buscado) {
        return contiene(raiz, buscado);
    }

    private boolean contiene(ArbolesLogicaTallerNodo actual, ArbolesLogicaTallerNodo buscado) {
        return actual != null && (actual == buscado || contiene(actual.hijoIzquierdo, buscado)
                || contiene(actual.hijoDerecho, buscado));
    }

    public List<Object> recorridoPreorden() {
        List<Object> resultado = new ArrayList<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(ArbolesLogicaTallerNodo nodo, List<Object> resultado) {
        if (nodo != null) {
            resultado.add(nodo.valor);
            preorden(nodo.hijoIzquierdo, resultado);
            preorden(nodo.hijoDerecho, resultado);
        }
    }

    public List<Object> recorridoInorden() {
        List<Object> resultado = new ArrayList<>();
        inorden(raiz, resultado);
        return resultado;
    }

    private void inorden(ArbolesLogicaTallerNodo nodo, List<Object> resultado) {
        if (nodo != null) {
            inorden(nodo.hijoIzquierdo, resultado);
            resultado.add(nodo.valor);
            inorden(nodo.hijoDerecho, resultado);
        }
    }

    public List<Object> recorridoPostorden() {
        List<Object> resultado = new ArrayList<>();
        postorden(raiz, resultado);
        return resultado;
    }

    private void postorden(ArbolesLogicaTallerNodo nodo, List<Object> resultado) {
        if (nodo != null) {
            postorden(nodo.hijoIzquierdo, resultado);
            postorden(nodo.hijoDerecho, resultado);
            resultado.add(nodo.valor);
        }
    }

    private void nodosPreorden(ArbolesLogicaTallerNodo nodo, List<ArbolesLogicaTallerNodo> resultado) {
        if (nodo != null) {
            resultado.add(nodo);
            nodosPreorden(nodo.hijoIzquierdo, resultado);
            nodosPreorden(nodo.hijoDerecho, resultado);
        }
    }

    public static ArbolesLogicaTallerBinario desdeRecorridos(String preorden, String inorden) {
        if (preorden == null || inorden == null || preorden.length() != inorden.length()) {
            throw new IllegalArgumentException("Los recorridos deben tener la misma cantidad de nodos.");
        }
        ArbolesLogicaTallerBinario arbol = new ArbolesLogicaTallerBinario();
        int[] posicionPreorden = {0};
        arbol.raiz = construir(preorden, inorden, 0, inorden.length() - 1, posicionPreorden);
        return arbol;
    }

    private static ArbolesLogicaTallerNodo construir(String preorden, String inorden, int inicio, int fin,
                                                    int[] posicionPreorden) {
        if (inicio > fin) return null;
        if (posicionPreorden[0] >= preorden.length()) {
            throw new IllegalArgumentException("Los recorridos no son válidos.");
        }
        char valor = preorden.charAt(posicionPreorden[0]++);
        int posicionInorden = inorden.indexOf(valor, inicio);
        if (posicionInorden < inicio || posicionInorden > fin) {
            throw new IllegalArgumentException("Los recorridos no son válidos.");
        }
        ArbolesLogicaTallerNodo nodo = new ArbolesLogicaTallerNodo(valor);
        nodo.hijoIzquierdo = construir(preorden, inorden, inicio, posicionInorden - 1, posicionPreorden);
        nodo.hijoDerecho = construir(preorden, inorden, posicionInorden + 1, fin, posicionPreorden);
        return nodo;
    }

    public static String postordenDesdeRecorridos(String preorden, String inorden) {
        ArbolesLogicaTallerBinario arbol = desdeRecorridos(preorden, inorden);
        StringBuilder resultado = new StringBuilder();
        for (Object valor : arbol.recorridoPostorden()) resultado.append(valor);
        return resultado.toString();
    }

    public void mostrarArbol() {
        mostrarNodo(raiz, "", true);
    }

    private void mostrarNodo(ArbolesLogicaTallerNodo nodo, String prefijo, boolean esIzquierdo) {
        if (nodo == null) return;
        mostrarNodo(nodo.hijoDerecho, prefijo + (esIzquierdo ? "│   " : "    "), false);
        System.out.println(prefijo + (esIzquierdo ? "└── " : "┌── ") + nodo.valor);
        mostrarNodo(nodo.hijoIzquierdo, prefijo + (esIzquierdo ? "    " : "│   "), true);
    }
}
