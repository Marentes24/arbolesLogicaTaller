import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.function.Supplier;

/** Ejemplo sencillo de un árbol binario de búsqueda. */
public class Main {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        int[] datos = {50, 30, 70, 20, 40, 60, 80, 35, 45};

        for (int dato : datos) {
            arbol.insertarValor(dato);
        }

        System.out.println("=== ÁRBOL BINARIO DE BÚSQUEDA ===\n");
        arbol.mostrarArbol();

        System.out.println("\n=== RECORRIDOS ===");
        mostrarRecorridosConTiempo(arbol);

        String preorden = "GEAIBMCLDFKJH";
        String inorden = "IABEGLDCFMKHJ";
        ArbolBinario arbolReconstruido = ArbolBinario.desdeRecorridos(preorden, inorden);

        System.out.println("\n=== ÁRBOL RECONSTRUIDO ===");
        System.out.println("Preorden: " + preorden);
        System.out.println("Inorden:  " + inorden);
        arbolReconstruido.mostrarArbol();
        System.out.println("\n=== RECORRIDOS DEL ÁRBOL RECONSTRUIDO ===");
        mostrarRecorridosConTiempo(arbolReconstruido);

        int[] datosPrueba = generarDatos(10_000);
        ArbolBinario arbolPrueba = new ArbolBinario();
        long tiempoNanosegundos = medirTiempoInsercion(arbolPrueba, datosPrueba);

        System.out.println("\n=== PRUEBA DE 10.000 DATOS ===");
        System.out.println("Datos insertados: " + arbolPrueba.tamanio());
        System.out.println("Tiempo de inserción: " + tiempoNanosegundos + " ns");
        System.out.println("Tiempo de inserción: " + (tiempoNanosegundos / 1_000_000.0) + " ms");
        System.out.println("\n=== RECORRIDOS DEL ÁRBOL DE PRUEBA ===");
        mostrarRecorridosConTiempo(arbolPrueba);
    }

    private static void mostrarRecorridosConTiempo(ArbolBinario arbol) {
        mostrarRecorridoConTiempo("Preorden", arbol::recorridoPreorden);
        //mostrarRecorridoConTiempo("Inorden", arbol::recorridoInorden);
       // mostrarRecorridoConTiempo("Postorden", arbol::recorridoPostorden);
    }

    private static void mostrarRecorridoConTiempo(String tipo, Supplier<List<Object>> recorrido) {
        long inicio = System.nanoTime();
        List<Object> resultado = recorrido.get();
        long tiempoNanosegundos = System.nanoTime() - inicio;

        System.out.println(tipo + ": " + resultado);
        System.out.println("Tiempo de " + tipo.toLowerCase() + ": " + tiempoNanosegundos
                + " ns (" + (tiempoNanosegundos / 1_000_000.0) + " ms)");
    }

    private static int[] generarDatos(int cantidad) {
        int[] datos = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            datos[i] = i;
        }

        for (int i = cantidad - 1; i > 0; i--) {
            int indiceAleatorio = ThreadLocalRandom.current().nextInt(i + 1);
            int temporal = datos[i];
            datos[i] = datos[indiceAleatorio];
            datos[indiceAleatorio] = temporal;
        }
        return datos;
    }

    private static long medirTiempoInsercion(ArbolBinario arbol, int[] datos) {
        long inicio = System.nanoTime();
        for (int dato : datos) {
            arbol.insertarValor(dato);
        }
        return System.nanoTime() - inicio;
    }
}
