/** Ejemplo sencillo de un árbol binario de búsqueda. */
public class ArbolesLogicaTallerMain {
    public static void main(String[] args) {
        ArbolesLogicaTallerBinario arbol = new ArbolesLogicaTallerBinario();
        int[] datos = {50, 30, 70, 20, 40, 60, 80, 35, 45};

        for (int dato : datos) {
            arbol.insertarValor(dato);
        }

        System.out.println("=== ÁRBOL BINARIO DE BÚSQUEDA ===\n");
        arbol.mostrarArbol();

        System.out.println("\n=== RECORRIDOS ===");
        System.out.println("Preorden  (raíz, izquierda, derecha): " + arbol.recorridoPreorden());
        System.out.println("Inorden   (izquierda, raíz, derecha): " + arbol.recorridoInorden());
        System.out.println("Postorden (izquierda, derecha, raíz): " + arbol.recorridoPostorden());

        String preorden = "GEAIBMCLDFKJH";
        String inorden = "IABEGLDCFMKHJ";
        ArbolesLogicaTallerBinario arbolReconstruido = ArbolesLogicaTallerBinario.desdeRecorridos(preorden, inorden);

        System.out.println("\n=== ÁRBOL RECONSTRUIDO ===");
        System.out.println("Preorden: " + preorden);
        System.out.println("Inorden:  " + inorden);
        arbolReconstruido.mostrarArbol();
        System.out.println("Postorden: "
                + ArbolesLogicaTallerBinario.postordenDesdeRecorridos(preorden, inorden));
    }
}
