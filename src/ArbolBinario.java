import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Árbol binario de búsqueda que también implementa el TAD Arbolable. */
public class ArbolBinario implements Arbol {
    private Nodo raiz;

    public void insertarValor(int dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private Nodo insertarRecursivo(Nodo nodo, int dato) {
        if (nodo == null) return new Nodo(dato);

        int datoActual = (Integer) nodo.valor;
        if (dato < datoActual) nodo.hijoIzquierdo = insertarRecursivo(nodo.hijoIzquierdo, dato);
        else if (dato > datoActual) nodo.hijoDerecho = insertarRecursivo(nodo.hijoDerecho, dato);
        return nodo;
    }

    @Override
    public int tamanio() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo nodo) {
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
        List<Nodo> nodos = new ArrayList<>();
        nodosPreorden(raiz, nodos);
        return nodos.iterator();
    }

    @Override
    public Object padreDe(Nodo nodo) {
        return buscarPadre(raiz, nodo);
    }

    private Nodo buscarPadre(Nodo actual, Nodo buscado) {
        if (actual == null || actual == buscado) return null;
        if (actual.hijoIzquierdo == buscado || actual.hijoDerecho == buscado) return actual;
        Nodo encontrado = buscarPadre(actual.hijoIzquierdo, buscado);
        return encontrado != null ? encontrado : buscarPadre(actual.hijoDerecho, buscado);
    }

    @Override
    public Lista hijosDe(Nodo nodo) {
        Lista resultado = new Lista();
        if (pertenece(nodo)) {
            if (nodo.hijoIzquierdo != null) resultado.add(nodo.hijoIzquierdo);
            if (nodo.hijoDerecho != null) resultado.add(nodo.hijoDerecho);
        }
        return resultado;
    }

    @Override
    public boolean esInterno(Nodo nodo) {
        return pertenece(nodo) && (nodo.hijoIzquierdo != null || nodo.hijoDerecho != null);
    }

    @Override
    public boolean esHoja(Nodo nodo) {
        return pertenece(nodo) && nodo.hijoIzquierdo == null && nodo.hijoDerecho == null;
    }

    @Override
    public boolean reemplazarNodo(Nodo actual, Nodo nuevo) {
        if (nuevo == null || !pertenece(actual)) return false;
        actual.valor = nuevo.valor;
        return true;
    }

    private boolean pertenece(Nodo buscado) {
        return contiene(raiz, buscado);
    }

    private boolean contiene(Nodo actual, Nodo buscado) {
        return actual != null && (actual == buscado || contiene(actual.hijoIzquierdo, buscado)
                || contiene(actual.hijoDerecho, buscado));
    }

    public List<Object> recorridoPreorden() {
        List<Object> resultado = new ArrayList<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(Nodo nodo, List<Object> resultado) {
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

    private void inorden(Nodo nodo, List<Object> resultado) {
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

    private void postorden(Nodo nodo, List<Object> resultado) {
        if (nodo != null) {
            postorden(nodo.hijoIzquierdo, resultado);
            postorden(nodo.hijoDerecho, resultado);
            resultado.add(nodo.valor);
        }
    }

    private void nodosPreorden(Nodo nodo, List<Nodo> resultado) {
        if (nodo != null) {
            resultado.add(nodo);
            nodosPreorden(nodo.hijoIzquierdo, resultado);
            nodosPreorden(nodo.hijoDerecho, resultado);
        }
    }

    public static ArbolBinario desdeRecorridos(String preorden, String inorden) {
        if (preorden == null || inorden == null || preorden.length() != inorden.length()) {
            throw new IllegalArgumentException("Los recorridos deben tener la misma cantidad de nodos.");
        }
        ArbolBinario arbol = new ArbolBinario();
        int[] posicionPreorden = {0};
        arbol.raiz = construir(preorden, inorden, 0, inorden.length() - 1, posicionPreorden);
        return arbol;
    }

    private static Nodo construir(String preorden, String inorden, int inicio, int fin,
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
        Nodo nodo = new Nodo(valor);
        nodo.hijoIzquierdo = construir(preorden, inorden, inicio, posicionInorden - 1, posicionPreorden);
        nodo.hijoDerecho = construir(preorden, inorden, posicionInorden + 1, fin, posicionPreorden);
        return nodo;
    }

    public static String postordenDesdeRecorridos(String preorden, String inorden) {
        ArbolBinario arbol = desdeRecorridos(preorden, inorden);
        StringBuilder resultado = new StringBuilder();
        for (Object valor : arbol.recorridoPostorden()) resultado.append(valor);
        return resultado.toString();
    }

    public void mostrarArbol() {
        mostrarNodo(raiz, "", true);
    }

    private void mostrarNodo(Nodo nodo, String prefijo, boolean esIzquierdo) {
        if (nodo == null) return;
        mostrarNodo(nodo.hijoDerecho, prefijo + (esIzquierdo ? "│   " : "    "), false);
        System.out.println(prefijo + (esIzquierdo ? "└── " : "┌── ") + nodo.valor);
        mostrarNodo(nodo.hijoIzquierdo, prefijo + (esIzquierdo ? "    " : "│   "), true);
    }
}
